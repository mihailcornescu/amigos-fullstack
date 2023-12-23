package com.mihailcornescu.customer;

import com.mihailcornescu.AbstractTestcontainers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService service;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        service = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        //given
        Customer customer = new Customer("Mike", "mike@mail.com", 32);
        service.insertCustmer(customer);

        //then
        assertThat(service.selectAllCustomers()).hasSize(1);
    }

    @Test
    void selectCustomerById() {
        //given
        String email = "nicki@mail.com";
        Customer customer = new Customer("Nicki", email, 32);
        service.insertCustmer(customer);
        Long id = getIdFromCustomerByEmail(email);

        //when
        Optional<Customer> actual = service.selectCustomerById(id);

        //then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectByCustomerId() {
        //given
        Long id = -1L;

        //when
        Optional<Customer> actual = service.selectCustomerById(id);

        //then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        //given
        String email = "charlotte@mail.com";
        Customer customer = new Customer("Charlotte", email, 27);

        //when
        service.insertCustmer(customer);

        //then
        Long id = getIdFromCustomerByEmail(email);
        Optional<Customer> actual = service.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void existsCustomerWithEmail() {
        //given
        String email = "john@mail.com";
        Customer customer = new Customer("John", email, 27);
        service.insertCustmer(customer);

        //then
        boolean result = service.existsCustomerWithEmail(email);

        //when
        assertThat(result).isTrue();

    }

    @Test
    void deleteCustomerById() {
        //given
        String email = "andrew@mail.com";
        Customer customer = new Customer("Andrew", email, 34);
        service.insertCustmer(customer);
        Long id = getIdFromCustomerByEmail(email);

        //when
        service.deleteCustomerById(id);

        //then
        assertThatThrownBy(() -> getIdFromCustomerByEmail(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @NotNull
    private Long getIdFromCustomerByEmail(String email) {
        return service.selectAllCustomers().stream().filter(c -> c.getEmail().equals(email)).map(Customer::getId).findFirst().orElseThrow();
    }
}