package com.mihailcornescu.customer;

import com.mihailcornescu.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import static com.mihailcornescu.customer.TestUtils.getRandomCustomer;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        //given
        Customer customer = getRandomCustomer();
        String email = customer.getEmail();
        underTest.save(customer);

        //when
        boolean result = underTest.existsCustomerByEmail(email);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenIdNotPresent() {
        //given
        String email = "mark@mail.com";

        //when
        boolean result = underTest.existsCustomerByEmail(email);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void existsCustomerById() {
        //given
        Customer customer = getRandomCustomer();
        String email = customer.getEmail();
        underTest.save(customer);
        Long alexId = underTest.findAll().stream().filter(c -> c.getEmail().equals(email)).map(Customer::getId).findFirst().orElseThrow();

        //when
        boolean result = underTest.existsCustomerById(alexId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void existsCustomerByIdFailsWhenIdNotPresent() {
        //given
        Long id = -1L;

        //when
        boolean result = underTest.existsCustomerById(id);

        //then
        assertThat(result).isFalse();
    }
}