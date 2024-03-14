package com.example.filesharing.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.filesharing.demo.costing.service.ForcastingService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ForcastingService forcasting;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
