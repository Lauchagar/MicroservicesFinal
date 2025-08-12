
package com.sa.clase.token.repository;

import com.sa.clase.token.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 2 PASO
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findOneByEmail(String email);
    
    Boolean existsByEmail(String email);
}
