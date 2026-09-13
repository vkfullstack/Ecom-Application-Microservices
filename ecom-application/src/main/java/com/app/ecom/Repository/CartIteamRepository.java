package com.app.ecom.Repository;

import com.app.ecom.Entity.CartItem;
import com.app.ecom.Entity.Product;
import com.app.ecom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartIteamRepository  extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user = :user AND c.product = :product")
    void deleteByUserAndProduct(User user, Product product);

    List findAllByUser(User user);

    void deleteAllByUser(User user);
}
