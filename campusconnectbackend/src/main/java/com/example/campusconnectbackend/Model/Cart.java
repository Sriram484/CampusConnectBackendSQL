
package com.example.campusconnectbackend.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Integer userId;

    @ElementCollection
    @CollectionTable(name = "cart_courses", joinColumns = @JoinColumn(name = "cart_id"))
    @Column(name = "course_id")
    private Set<Long> courseIds = new HashSet<>();
}
