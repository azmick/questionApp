package com.example.questionapp.service;

import com.example.questionapp.model.User;
import com.example.questionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
        }

        // Kullanıcının detaylarını Spring Security'nin kullanabileceği şekilde döndürüyoruz.
        return org.springframework.security.core.userdetails.User
                .withUsername(user.get().getUsername())
                .password(user.get().getPassword())  // BCrypt ile şifrelenmiş olmalı
                .authorities("USER")  // Kullanıcıya roller ekleyebilirsiniz
                .build();
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
