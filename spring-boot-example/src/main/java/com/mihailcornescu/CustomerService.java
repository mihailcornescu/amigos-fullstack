package com.mihailcornescu;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> selectAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer selectCustomerById(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid id"));
    }

}
