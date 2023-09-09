package de.telran.lesson3.service_layer.jpa;

import de.telran.lesson3.domain_layer.entity.Cart;
import de.telran.lesson3.domain_layer.entity.Customer;
import de.telran.lesson3.domain_layer.entity.Product;
import de.telran.lesson3.domain_layer.entity.jpa.JpaCart;
import de.telran.lesson3.domain_layer.entity.jpa.JpaCustomer;
import de.telran.lesson3.domain_layer.entity.jpa.JpaProduct;
import de.telran.lesson3.repository_layer.jpa.JpaCustomerRepository;
import de.telran.lesson3.repository_layer.jpa.JpaProductRepository;
import de.telran.lesson3.service_layer.CustomerService;
import de.telran.lesson3.service_layer.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class JpaCustomerService implements CustomerService {

    JpaCustomerRepository repository;
    JpaProductRepository productRepository;

    @Autowired
    public JpaCustomerService(JpaCustomerRepository repository, JpaProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Customer getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void add(Customer customer) {
        //создаю пользователя и сразу создаю корзину. Надо добавить опцию Cascade.CREATE
        var jpaCustomer = new JpaCustomer(0, customer.getName(), null);
        var jpaCart = new JpaCart(0, jpaCustomer, new ArrayList<>());
        jpaCustomer.setCart(jpaCart);

        repository.save(jpaCustomer);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public int getCount() {
        return repository.findAll().size();
    }

    @Override
    public double getTotalPriceById(int id) {

        Optional<JpaCustomer> customer = repository.findById(id);

        if (customer.isPresent()) {
            return customer.get().getCart().getTotalPrice();
        }

        return 0;
    }

    @Override
    public double getAveragePriceById(int id) {
        //опять же пока так, но я подозраваю что это все надо передать нативным запросом что вернет из бд
        //только одну цифру а не кучу обьектов

        Optional<JpaCustomer> customer = repository.findById(id);
        Cart cart;

        if (customer.isPresent()) {
            cart = customer.get().getCart();
        } else {
            return 0;
        }

        if (!cart.getProducts().isEmpty()) {
            return cart.getTotalPrice() / cart.getProducts().size();
        } else {
            return 0;
        }
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        //думаю тут нужна проверка на ispresent. Хотя думаю это скорее должно делаться native
        //запросом с case операторами чтоб оптимизировать запрос, но пока так:
        Optional<JpaCustomer> customer = repository.findById(customerId);
        Optional<JpaProduct> product = productRepository.findById(productId);
        if (customer.isPresent() && product.isPresent()) {
            repository.addProductToCartById(customerId, productId);
        }
    }

    @Override
    public void deleteFromCart(int customerId, int productId) {
        //проверка на ispresent не нужна, ведь субд просто проигнорирует удаление несуществующего id

//        Optional<JpaCustomer> customer = repository.findById(customerId);
//        Optional<JpaProduct> product = productRepository.findById(productId);
//        if (customer.isPresent() && product.isPresent()) {
            repository.removeProductFromCartById(customerId, productId);
//        }
    }

    @Override
    public void clearCart(int customerId) {
        //таже история. Проверка не нужна
//        Optional<JpaCustomer> customer = repository.findById(customerId);
//        if(customer.isPresent()) {
            repository.removeAllCartProductsByCartId(customerId);
//        }
    }
}