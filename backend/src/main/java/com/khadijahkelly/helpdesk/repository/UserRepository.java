package com.khadijahkelly.helpdesk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.khadijahkelly.helpdesk.model.User;

public interface UserRepository 
  extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}

