package com.mihailcornescu.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                         CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer;
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustmer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT count(*)
                FROM customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Long id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }
}
