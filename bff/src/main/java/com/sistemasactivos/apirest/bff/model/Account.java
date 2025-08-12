package com.sistemasactivos.apirest.bff.model;

import java.io.Serializable;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable{
    private Integer id;
    private String cbu;    
    private String banco;
    private String nombreTitular;
}
