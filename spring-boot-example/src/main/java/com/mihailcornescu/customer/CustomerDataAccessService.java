package com.mihailcornescu.customer;

import com.mihailcornescu.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("list")
public class CustomerDataAccessService implements CustomerDao {

    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(1L, "Alex", "alex@gmail.com", 30));
        customers.add(new Customer(2L, "Jamila", "jamila@gmail.com", 29));
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream()
                        .filter(c -> Objects.equals(c.getId(), id))
                        .findFirst();
    }

    @Override
    public void insertCustmer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomerById(Long id) {
        Optional<Customer> customer = Optional.ofNullable(customers.stream().filter(c -> c.getId().equals(id)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] was not found.".formatted(id))));
        customer.ifPresent(customers::remove);
    }

}
