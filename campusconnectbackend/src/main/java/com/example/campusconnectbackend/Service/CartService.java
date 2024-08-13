package com.example.campusconnectbackend.Service;

import com.example.campusconnectbackend.Model.Cart;
import com.example.campusconnectbackend.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart createCart(Integer userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    public Cart addCourseToCart(Integer userId, Long courseId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = createCart(userId);
        }
        cart.getCourseIds().add(courseId);
        return cartRepository.save(cart);
    }

    public Cart removeCourseFromCart(Integer userId, Long courseId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getCourseIds().remove(courseId);
            return cartRepository.save(cart);
        }
        return null;
    }
}
