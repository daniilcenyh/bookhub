package com.hamming.bookhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@Cacheable
@SpringBootApplication
public class BookhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookhubApplication.class, args);
	}

}
