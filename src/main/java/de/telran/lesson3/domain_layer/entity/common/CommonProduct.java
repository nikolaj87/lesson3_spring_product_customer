package de.telran.lesson3.domain_layer.entity.common;

import de.telran.lesson3.domain_layer.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonProduct implements Product {

    private int id;
    private String name;
    private double price;
}