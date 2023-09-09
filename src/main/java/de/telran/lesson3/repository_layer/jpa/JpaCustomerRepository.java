package de.telran.lesson3.repository_layer.jpa;

import de.telran.lesson3.domain_layer.entity.jpa.JpaCustomer;
import de.telran.lesson3.domain_layer.entity.jpa.JpaProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCustomerRepository extends JpaRepository<JpaCustomer, Integer> {

    @Transactional
    void deleteByName(String name);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `shop_jpa`.`cart_product` (`cart_id`, `product_id`) VALUES (:customerId, :productId)", nativeQuery = true)
    void addProductToCartById(@Param("customerId") int customerId, @Param("productId") int productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `shop_jpa`.`cart_product` WHERE `cart_id` = :customerId AND `product_id` = :productId", nativeQuery = true)
    void removeProductFromCartById(@Param("customerId") int customerId, @Param("productId") int productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `shop_jpa`.`cart_product` WHERE `cart_id` = :customerId", nativeQuery = true)
    void removeAllCartProductsByCartId(@Param("customerId") int customerId);
}
