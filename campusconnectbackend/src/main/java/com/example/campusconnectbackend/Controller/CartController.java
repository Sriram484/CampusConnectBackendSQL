package com.example.campusconnectbackend.Controller;

import com.example.campusconnectbackend.Model.Cart;
import com.example.campusconnectbackend.Model.Users;
import com.example.campusconnectbackend.Repository.UserRepo;
import com.example.campusconnectbackend.Service.CartService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminuser/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepo userrepo;

    @PostMapping("/add")
    public ResponseEntity<String> addCourseToCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long courseId) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String email = userDetails.getUsername();
        Optional<Users> optionalUser = userrepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            Integer userId = user.getId();

            try {
                cartService.addCourseToCart(userId, courseId);
                return ResponseEntity.ok("Course added to cart successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error adding course to cart: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeCourseFromCart(@RequestParam Integer userId, @RequestParam Long courseId) {
        try {
            cartService.removeCourseFromCart(userId, courseId);
            return ResponseEntity.ok("Course removed from cart successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error removing course from cart: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Integer userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            if (cart != null) {
                return ResponseEntity.ok(cart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
