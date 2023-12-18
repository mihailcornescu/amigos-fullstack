package com.mihailcornescu;

import com.mihailcornescu.customer.Customer;
import com.mihailcornescu.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringBootExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);


	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository) {
		return args -> {
			List<Customer> customers = new ArrayList<>();
			customers.add(new Customer("Alex", "alex@gmail.com", 30));
			customers.add(new Customer("Jamila", "jamila@gmail.com", 29));
			customerRepository.saveAll(customers);
		};
	}
}
