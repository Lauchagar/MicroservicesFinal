package com.sistemasactivos.accountweb.model;


import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private String name;
    private String email;
    private String password;
    private boolean enabled;
    
    private Set<Role> roles = new HashSet<>();
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {
    private String name;
    }
    
}
