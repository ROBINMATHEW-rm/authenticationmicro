package com.authenticationmicro.authenticationmicro.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory UserDetailsService for the Auth Service.
 * Replace the user store with a database-backed implementation for production.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    // In-memory user store: username -> encoded password
    private final Map<String, String> users = new ConcurrentHashMap<>();

    public AppUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        // Seed a default admin user for development purposes only
        users.put("admin", passwordEncoder.encode("admin123"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodedPassword = users.get(username);
        if (encodedPassword == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return User.withUsername(username)
                .password(encodedPassword)
                .roles("USER")
                .build();
    }

    /**
     * Registers a new user. Returns false if the username is already taken.
     */
    public boolean registerUser(String username, String rawPassword) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, passwordEncoder.encode(rawPassword));
        return true;
    }
}
