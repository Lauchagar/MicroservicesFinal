package com.sistemasactivos.metrics.repository;

import com.sistemasactivos.metrics.model.DiskSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskSpaceRepository extends JpaRepository<DiskSpace, Integer> {
    
}
