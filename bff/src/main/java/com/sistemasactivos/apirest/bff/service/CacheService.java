package com.sistemasactivos.apirest.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean hasRequiredRoles(String token, String requestType) {
        String admin = "ROLE_ADMIN";
        String create = "ROLE_CREATE";
        
        List<String> roles = getRoles(token);
        
        String rolesString = Arrays.toString(roles.toArray());
        rolesString = rolesString.replace("[", "");
        rolesString = rolesString.replace("]", "");
        String[] rolesArray = rolesString.split(",\\s*");
        
        List<String> rolesList = new ArrayList<>();
        rolesList.addAll(Arrays.asList(rolesArray));
        
        if (!roles.isEmpty()) {
            if(requestType.equals("POST") || requestType.equals("PUT")){
                return rolesList.contains(admin) || rolesList.contains(create);
            }
            if(requestType.equals("DELETE")){
                return rolesList.contains(admin);
            }
        }
        return false;
    }   
    
    private List<String> getRoles(String token) {
        Cache cache = cacheManager.getCache("jwtTokens");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(token);
            if (valueWrapper != null) {
                List<String> roles = (List<String>) valueWrapper.get();
                return roles;
            }
        }
        return new ArrayList<>();
    }    
}

