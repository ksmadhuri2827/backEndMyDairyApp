package com.dms.springcloud;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class AngularentriesregistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngularentriesregistrationApplication.class, args);
	}

}
