package com.mihailcornescu.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRowMapperTest {

    @Mock
    private ResultSet rs;

    @Test
    void mapRow() throws SQLException {
        //given
        Long id = 10L;
        String name = "Alex";
        String email = "alex@mail.com";
        int age = 20;
        when(rs.getLong("id")).thenReturn(id);
        when(rs.getString("name")).thenReturn(name);
        when(rs.getString("email")).thenReturn(email);
        when(rs.getInt("age")).thenReturn(age);
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        //when
        Customer customer = customerRowMapper.mapRow(rs, 1);

        //then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo("Mike");
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getAge()).isEqualTo(age);
    }
}