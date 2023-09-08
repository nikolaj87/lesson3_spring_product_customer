package de.telran.lesson3.service_layer.common;

import de.telran.lesson3.domain_layer.entity.Product;
import de.telran.lesson3.repository_layer.ProductRepository;
import de.telran.lesson3.service_layer.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommonProductService implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }

    @Override
    public Product getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void add(Product product) {
        repository.add(product.getName(), product.getPrice());
    }

    @Override
    public void deleteById(int id) {
        repository.delete(id);
    }

    @Override
    public void deleteByName(String name) {
        int idToDelete = repository.getAll().stream().filter(x -> x.getName().equals(name)).findFirst().get().getId();
        repository.delete(idToDelete);
    }

    @Override
    public int getCount() {
        return repository.getAll().size();
    }

    @Override
    public double getTotalPrice() {
        return repository.getAll().stream().mapToDouble(Product::getPrice).reduce(Double::sum).orElse(0);
    }

    @Override
    public double getAveragePrice() {
        return getTotalPrice() / getCount();
    }
}