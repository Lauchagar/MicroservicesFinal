package com.sistemasactivos.metrics.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="memory_usage")
public class MemoryUsage extends Base {
    private LocalDateTime horario;
    private String microservice; 
    private double memory_used_mb;
    private String status;
    private double memory_max_mb;
    
}
