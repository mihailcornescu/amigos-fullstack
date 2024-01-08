package com.mihailcornescu.customer;

import com.mihailcornescu.exception.DuplicateResourceException;
import com.mihailcornescu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Long id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id [%d] does not exist!".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest request) {
        String email = request.email();
        if (customerDao.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException("customer with email [%s] already exists!".formatted(email));
        }
        customerDao.insertCustmer(
                new Customer(
                        request.name(),
                        request.email(),
                        request.age()
                )
        );
    }

    public void deleteCustomerById(Long id) {
        if (!customerDao.existsCustomerWithId(id)) {
            throw new ResourceNotFoundException("customer with id [%s] was not found.".formatted(id));
        }
        customerDao.deleteCustomerById(id);
    }

}
