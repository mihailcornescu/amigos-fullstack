package com.mihailcornescu.customer;

import net.datafaker.Faker;

public class TestUtils {

    public static Customer getRandomCustomer() {
        Faker faker = new Faker();
        int age = faker.number().numberBetween(1, 40);
        return new Customer(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                age,
                age % 2 == 0 ? Gender.FEMALE : Gender.MALE);
    }

    public static Customer getRandomCustomerWithId(Long id) {
        Faker faker = new Faker();
        int age = faker.number().numberBetween(1, 40);
        return new Customer(
                id,
                faker.name().fullName(),
                faker.internet().emailAddress(),
                age,
                age % 2 == 0 ? Gender.FEMALE : Gender.MALE);
    }

    public static CustomerRegistrationRequest getRandomCustomerRegistrationRequest() {
        Faker faker = new Faker();
        int age = faker.number().numberBetween(1, 40);
        return new CustomerRegistrationRequest(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                age,
                age % 2 == 0 ? Gender.FEMALE : Gender.MALE);
    }
}
