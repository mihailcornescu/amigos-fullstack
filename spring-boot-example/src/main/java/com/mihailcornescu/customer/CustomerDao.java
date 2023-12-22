package com.mihailcornescu.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Long id);
    void insertCustmer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    void deleteCustomerById(Long id);
}
