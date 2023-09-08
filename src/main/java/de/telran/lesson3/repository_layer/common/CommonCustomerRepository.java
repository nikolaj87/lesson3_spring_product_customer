package de.telran.lesson3.repository_layer.common;

import de.telran.lesson3.domain_layer.database.DataBase;
import de.telran.lesson3.domain_layer.entity.Customer;
import de.telran.lesson3.domain_layer.entity.Product;
import de.telran.lesson3.repository_layer.CustomerRepository;
import de.telran.lesson3.repository_layer.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public class CommonCustomerRepository implements CustomerRepository {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataBase dataBase;

    @Override
    public List<Customer> getAll() {
        try {
            return dataBase.select("Select all customers").stream().map(x -> (Customer) x).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        try {
            return (Customer) dataBase.select("Select customer where id = " + id).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name) {
        try {
            dataBase.execute("Add new customer name = " + name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            dataBase.execute("Delete customer where id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        Customer customer = getById(customerId);
        Product product = productRepository.getById(productId);
        customer.getCart().addProduct(product);
    }

    @Override
    public void deleteFromCart(int customerId, int productId) {
        Customer customer = getById(customerId);
        customer.getCart().getProducts().removeIf(x -> x.getId() == productId);
    }

    @Override
    public void clearCart(int customerId) {
        Customer customer = getById(customerId);
        customer.getCart().getProducts().clear();
    }
}