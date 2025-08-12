package com.sistemasactivos.metrics.repository;

import com.sistemasactivos.metrics.model.MemoryUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryUsageRepository extends JpaRepository<MemoryUsage, Integer> {
    
}
