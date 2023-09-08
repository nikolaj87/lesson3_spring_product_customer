package de.telran.lesson3.controller_layer;

import de.telran.lesson3.domain_layer.entity.common.CommonProduct;
import de.telran.lesson3.domain_layer.entity.Product;
import de.telran.lesson3.service_layer.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Product add(@RequestBody CommonProduct product) {
        service.add(product);
        return product;
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

    @GetMapping("/total")
    public double getTotalPrice() {
        return service.getTotalPrice();
    }

    @GetMapping("/average")
    public double getAverage() {
        return service.getAveragePrice();
    }
}