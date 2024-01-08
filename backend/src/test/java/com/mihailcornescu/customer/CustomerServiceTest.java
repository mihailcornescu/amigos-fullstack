package com.mihailcornescu.customer;

import com.mihailcornescu.exception.DuplicateResourceException;
import com.mihailcornescu.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;
    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        //when
        underTest.getAllCustomers();

        //then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomerById() {
        //given
        Long id = 10L;
        Customer customer = new Customer(id, "Alex", "alex@mail.com", 19);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //when
        Customer actual = underTest.getCustomerById(id);

        //then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenetCustomerByIdEmptyOptional() {
        //given
        Long id = 10L;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer with id [%d] does not exist!".formatted(id));
    }

    @Test
    void addCustomer() {
        //given
        String email = "alex@mail.com";
        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex", email, 21);

        //when
        underTest.addCustomer(request);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustmer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());

    }

    @Test
    void addCustomerDuplicateEmail() {
        //given
        String email = "alex@mail.com";
        when(customerDao.existsCustomerWithEmail(email)).thenReturn(true);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex", email, 21);

        //then
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("customer with email [%s] already exists!".formatted(email));
    }

    @Test
    void deleteCustomerById() {
        //given
        Long id = 10L;
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);

        //when
        underTest.deleteCustomerById(id);

        //then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void deleteCustomerByIdFailNotExists() {
        //given
        Long id = 10L;
        when(customerDao.existsCustomerWithId(id)).thenReturn(false);

        //then
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("customer with id [%s] was not found.".formatted(id));
    }
}