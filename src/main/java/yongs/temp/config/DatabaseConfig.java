package yongs.temp.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatabaseConfig {
	@Bean
	@Primary
	@ConfigurationProperties("batch.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Bean(name="appDataSource")
	@ConfigurationProperties("temp.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }
}
