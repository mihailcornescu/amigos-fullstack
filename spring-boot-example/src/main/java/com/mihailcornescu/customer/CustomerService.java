package com.mihailcornescu.customer;

import com.mihailcornescu.exception.DuplicateResourceException;
import com.mihailcornescu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> selectAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer selectCustomerById(Long id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("invalid id"));
    }

    public void addCustomer(CustomerRegistrationRequest request) {
        String email = request.email();
        if (customerDao.existsCustomeWithEmail(email)) {
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
        customerDao.deleteCustomerById(id);
    }

}
