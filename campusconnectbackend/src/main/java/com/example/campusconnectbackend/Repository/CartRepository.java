package com.example.campusconnectbackend.Repository;

import com.example.campusconnectbackend.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Integer userId);
}
