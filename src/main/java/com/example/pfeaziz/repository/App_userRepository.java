package com.example.pfeaziz.repository;


import com.example.pfeaziz.model.App_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface App_userRepository extends JpaRepository<App_user, Long> {
    Optional<App_user> findByUsername(String username);
    
}
