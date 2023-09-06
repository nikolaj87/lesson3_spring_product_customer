package de.telran.lesson3.repository_layer;

import de.telran.lesson3.domain_layer.entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static de.telran.lesson3.domain_layer.database.MySqlConnector.getConnection;

public class MySqlCustomerRepository implements CustomerRepository {

    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM customer as c left join customer_product as cp on c.id = cp.customer_id left join product as p on cp.product_id = p.id;";

            ResultSet resultSet = connection.createStatement().executeQuery(query);
            List<Customer> result = new ArrayList<>();
            resultSet.next();

            do {
                //создаю сущности собираю ответ
                var cart = new CommonCart();
                var customer = new CommonCustomer(resultSet.getInt(1), resultSet.getString(2), cart);
                result.add(customer);
                //наполняю корзину текущнго кастомера
                while (resultSet.getInt(1) == customer.getId()) {
                    //проверка не пуста ли корзина
                    if (resultSet.getInt(6) != 0 || resultSet.getString(7) != null) {
                        cart.addProduct(new CommonProduct(resultSet.getInt(6),
                                resultSet.getString(7), resultSet.getDouble(8)));
                    }
                    if (!resultSet.next()) break;
                }
            } while (!resultSet.isAfterLast());

            return result;
            // Создать список клиентов и наполнить корзины каждого клиента их товарами
            // Учесть момент, что у клиента вообще может не быть никаких товаров,
            // в таком случае корзина просто должна быть пустая.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("SELECT * FROM customer as c left join customer_product as cp on c.id = cp.customer_id left join product as p on cp.product_id = p.id where c.id = %d;", id);

            ResultSet resultSet = connection.createStatement().executeQuery(query);
            var cart = new CommonCart();
            Customer customer = null;
            while (resultSet.next()) {
                if (resultSet.getInt(6) != 0 || resultSet.getString(7) != null ) {
                    cart.addProduct(new CommonProduct(resultSet.getInt(6), resultSet.getString(7),
                            resultSet.getDouble(8)));
                }
                if (resultSet.isLast()) {
                    customer = new CommonCustomer(resultSet.getInt(1),
                            resultSet.getString(2), cart);
                }
            }

            return customer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name) {
        try (Connection connection = getConnection()) {
            String query = String.format("INSERT INTO `customer` (`name`) VALUES ('%s');", name);
            connection.createStatement().execute(query);
//            ResultSet resultSet = connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer` WHERE (`id` = '%d');", id);
            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            String query = String.format("INSERT INTO `customer_product` (`customer_id`, `product_id`) VALUES ('%d', '%d');", customerId, productId);

            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFromCart(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer_product` WHERE (`customer_id` = '%d' and `product_id` = '%d');", customerId, productId);

            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int customerId) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `customer_product` WHERE (`customer_id` = '%d');", customerId);

            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
