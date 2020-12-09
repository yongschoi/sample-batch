package yongs.temp.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yongs.temp.vo.Product;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProcessorConvertJobConfig {
	public static final String JOB_NAME = "processorConvertJob";
	public static final String BEAN_PREFIX = JOB_NAME + "_";
    
	private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;	
    @Autowired
    @Qualifier("appDataSource")
    private final DataSource dataSource; // DataSource DI
    
    @Value("${chunkSize:1000}")
    private int chunkSize;
    
    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(step())
                .build();
    }
    
    @Bean(BEAN_PREFIX + "step")
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(BEAN_PREFIX + "step")
                .<Product, String>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    @Bean
    public JdbcCursorItemReader<Product> reader() {
        return new JdbcCursorItemReaderBuilder<Product>()
        		.name(BEAN_PREFIX + "reader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Product.class))
                .sql("SELECT code, name, amount, refreshTime FROM product")               
                .build();
    }
    
    @Bean
    @StepScope
    public ItemProcessor<Product, String> processor() {
        return p -> {
        	if(p.getAmount() > 55L) return null;
            return p.getName();
        };
    }
    
    private ItemWriter<String> writer() {
        return items -> {
            for (String item : items) {
                log.info("Product Name={}", item);
            }
        };
    }
}
