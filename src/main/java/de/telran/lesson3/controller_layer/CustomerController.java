package de.telran.lesson3.controller_layer;

import de.telran.lesson3.domain_layer.entity.common.CommonCustomer;
import de.telran.lesson3.domain_layer.entity.Customer;
import de.telran.lesson3.service_layer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public List<Customer> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Customer add(@RequestBody CommonCustomer customer) {
        service.add(customer);
        return customer;
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/deletename/{name}")
    public void delete(@PathVariable String name) {
        service.deleteByName(name);
    }

    @GetMapping("/count")
    public int getCount() {
        return service.getCount();
    }

    @GetMapping("/total/{id}")
    public double getTotalPrice(@PathVariable int id) {
        return service.getTotalPriceById(id);
    }

    @GetMapping("/average/{id}")
    public double getAverage(@PathVariable int id) {
        return service.getAveragePriceById(id);
    }

    @GetMapping("/add/{customerId}/{productId}")
    public void addToCart(@PathVariable int customerId, @PathVariable int productId) {
        service.addToCartById(customerId, productId);
    }

    @GetMapping("/delete/{customerId}/{productId}")
    public void deleteFromCart(@PathVariable int customerId, @PathVariable int productId) {
        service.deleteFromCart(customerId, productId);
    }

    @GetMapping("/clear/{id}")
    public void clearCart(@PathVariable int id) {
        service.clearCart(id);
    }
}