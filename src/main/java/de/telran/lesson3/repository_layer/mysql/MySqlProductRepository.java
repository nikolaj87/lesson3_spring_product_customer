package de.telran.lesson3.repository_layer.mysql;

import de.telran.lesson3.domain_layer.entity.common.CommonProduct;
import de.telran.lesson3.domain_layer.entity.Product;
import de.telran.lesson3.repository_layer.ProductRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.telran.lesson3.domain_layer.database.MySqlConnector.getConnection;

public class MySqlProductRepository implements ProductRepository {

    @Override
    public List<Product> getAll() {
        try (Connection connection = getConnection()) {
            String query = "select * from product;";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Product> result = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                result.add(new CommonProduct(id, name, price));
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("select * from product where id = %d;", id);
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            if (resultSet.next()) {
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                return new CommonProduct(id, name, price);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void add(String name, double price) {
        try (Connection connection = getConnection()) {
            String query = String.format(Locale.US, "INSERT INTO `product` (`name`, `price`) " +
                    "VALUES ('%s', '%.2f');", name, price);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `product` WHERE (`id` = '%d');", id);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
