package com.sistemasactivos.accountweb.model;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private String cbu;    
    private String banco;
    private String nombreTitular;
}
