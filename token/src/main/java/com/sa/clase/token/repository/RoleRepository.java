package com.sa.clase.token.repository;

import com.sa.clase.token.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    public Role findByName(String name);
}
