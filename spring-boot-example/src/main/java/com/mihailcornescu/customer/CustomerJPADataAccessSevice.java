package com.mihailcornescu.customer;

import com.mihailcornescu.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessSevice implements CustomerDao {
    public CustomerJPADataAccessSevice(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustmer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsCustomerById(id)) {
            throw new ResourceNotFoundException("customer with id [%s] was not found.".formatted(id));
        }
        customerRepository.deleteById(id);
    }
}
