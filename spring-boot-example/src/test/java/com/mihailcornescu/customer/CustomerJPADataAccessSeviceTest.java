package com.mihailcornescu.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessSeviceTest {

    private CustomerJPADataAccessSevice undertTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        undertTest = new CustomerJPADataAccessSevice(customerRepository);
    }

    @Test
    void selectAllCustomers() {
        //when
        undertTest.selectAllCustomers();

        //then
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        //given
        Long id = 1L;

        //when
        undertTest.selectCustomerById(id);

        //then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustmer() {
        //given
        String email = "maria@mail.com";
        Customer customer = new Customer("Maria", email, 30);

        //when
        undertTest.insertCustmer(customer);

        //then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsCustomerWithEmail() {
        //given
        String email = "alexandra@mail.com";

        //when
        undertTest.existsCustomerWithEmail(email);

        //then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerWithId() {
        //given
        Long id = 1L;

        //when
        undertTest.existsCustomerWithId(id);

        //then
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        //given
        Long id = 1L;

        //when
        undertTest.deleteCustomerById(id);

        //then
        verify(customerRepository).deleteById(id);
    }

}