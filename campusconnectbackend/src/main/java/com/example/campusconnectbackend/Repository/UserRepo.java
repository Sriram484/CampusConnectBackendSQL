package com.example.campusconnectbackend.Repository;


import com.example.campusconnectbackend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepo extends JpaRepository<Users,Integer>{

    Optional<Users> findByEmail(String email);

} 