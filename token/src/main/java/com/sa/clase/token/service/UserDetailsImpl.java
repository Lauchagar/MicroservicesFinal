package com.sa.clase.token.service;

import com.sa.clase.token.model.Role;
import com.sa.clase.token.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails{
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        
        return authorities;
    }

    @Override
    public String getPassword() {return user.getPassword();
    }

    @Override
    public String getUsername() { return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true;
    }

    @Override
    public boolean isAccountNonLocked() {return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {return true;
    }

    @Override
    public boolean isEnabled() { return true;
    }
    
    public String getName(){ return user.getName();
    }
}
