package com.sa.clase.token.service;

import com.sa.clase.token.model.Role;
import com.sa.clase.token.model.User;
import com.sa.clase.token.repository.RoleRepository;
import com.sa.clase.token.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void save(User registrationRequest) throws Exception{
        try{
            // Verificar si el usuario ya existe
            if (userRepository.existsByEmail(registrationRequest.getEmail())) {
                throw new Exception("User already exists");
            }
            // Crear un nuevo usuario
            User newUser = new User();
            newUser.setName(registrationRequest.getName());
            newUser.setEmail(registrationRequest.getEmail());
            // Encriptar la contraseña
            newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            // Setear otros campos según sea necesario
            newUser.setEnabled(true);
            // Guardar los roles del usuario
            Set<Role> roles = new HashSet<>();
            for (Role roleDto : registrationRequest.getRoles()) {
                Role role = roleRepository.findByName(roleDto.getName());
                roles.add(role);
            }
            newUser.setRoles(roles);
            userRepository.save(newUser);
        }catch(Exception e){
            throw new Exception("Error al guardar el usuario");
        }
    }
    
    public boolean deleteById(Long id) throws Exception{
        try {
            if(!userRepository.existsById(id)){
            throw new Exception("User no exists");
        }
        userRepository.deleteById(id);
        return true;
        } catch (Exception e) {
            throw new Exception("Error al eliminar el usuario");
        }
        
    }
}
