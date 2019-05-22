package com.hu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.hu.mapper"})
public class SpirngBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpirngBootApplication.class, args);
	}
}
