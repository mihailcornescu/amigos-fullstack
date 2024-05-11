package com.mihailcornescu;

import com.mihailcornescu.customer.Customer;
import com.mihailcornescu.customer.CustomerRepository;
import com.mihailcornescu.customer.Gender;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@RestController
public class Main {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ApplicationRunner initializeDatabase() {
        return args -> {
            if (!customerRepository.findAll().isEmpty()) {
                return;
            }
            Faker faker = new Faker();
            List<Customer> customers = new ArrayList<>();
            IntStream.range(1, 10).forEach(i ->
                    customers.add(new Customer(
                            faker.name().fullName(),
                            faker.internet().emailAddress(),
                            faker.number().numberBetween(1, 40),
                            i % 2 == 0 ? Gender.FEMALE : Gender.MALE)
                    )
            );
            customerRepository.saveAll(customers);
        };
    }

}
