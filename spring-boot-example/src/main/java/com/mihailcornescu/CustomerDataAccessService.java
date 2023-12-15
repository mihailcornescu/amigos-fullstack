package com.mihailcornescu;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerDataAccessService implements CustomerDao {

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Alex", "alex@gmail.com", 30));
        customers.add(new Customer(2, "Jamila", "jamila@gmail.com", 29));
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                        .filter(c -> Objects.equals(c.getId(), id))
                        .findFirst();
    }
}
