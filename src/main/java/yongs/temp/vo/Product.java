package yongs.temp.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
	private String code;	
	private String name;
	private Long amount;
	private LocalDateTime refreshtime;
}
