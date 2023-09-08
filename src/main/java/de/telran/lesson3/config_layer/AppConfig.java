package de.telran.lesson3.config_layer;

import de.telran.lesson3.domain_layer.database.DataBase;
import de.telran.lesson3.domain_layer.database.SimpleDataBase;
import de.telran.lesson3.repository_layer.*;
import de.telran.lesson3.repository_layer.mysql.MySqlCustomerRepository;
import de.telran.lesson3.repository_layer.mysql.MySqlProductRepository;
import de.telran.lesson3.service_layer.*;
import de.telran.lesson3.service_layer.common.CommonCustomerService;
import de.telran.lesson3.service_layer.common.CommonProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DataBase dataBase() {
        return new SimpleDataBase();
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new MySqlCustomerRepository();
    }

    @Bean
    public ProductRepository productRepository() {
        return new MySqlProductRepository();
    }

    @Bean
    public CustomerService customerService() {
        return new CommonCustomerService();
    }

//    @Bean
//    public ProductService productService() {
//        return new CommonProductService();
//    }
}