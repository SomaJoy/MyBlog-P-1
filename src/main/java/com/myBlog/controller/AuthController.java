package com.myBlog.controller;

import com.myBlog.entity.Role;
import com.myBlog.entity.User;
import com.myBlog.payload.UserDto;
import com.myBlog.repository.RoleRepository;
import com.myBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        if(userRepository.existsByUsername(userDto.getUsername())){
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(userDto.getEmail())){
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByRoleType(userDto.getRoleType()).get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoleSet(roleSet);

        User userSaved = userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
