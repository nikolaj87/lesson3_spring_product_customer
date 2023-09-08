package de.telran.lesson3.domain_layer.entity.common;

import de.telran.lesson3.domain_layer.entity.Cart;
import de.telran.lesson3.domain_layer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonCustomer implements Customer {

    private int id;
    private String name;
    private Cart cart;
}