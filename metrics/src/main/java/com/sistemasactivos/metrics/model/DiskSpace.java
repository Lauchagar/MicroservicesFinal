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
@Table(name="disk_space")
public class DiskSpace extends Base{
    private LocalDateTime horario;
    private String microservice; 
    private double disk_free_gb;
    private String status;
    private double disk_total_gb;
}