package com.mihailcornescu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class SpringBootExampleApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}





}
