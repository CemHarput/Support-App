package com.SupportApplication.SupportApp.User.service;

import com.SupportApplication.SupportApp.User.model.Role;
import com.SupportApplication.SupportApp.User.model.User;
import com.SupportApplication.SupportApp.User.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService implements UserDetailsService {
    private final Map<String, User> users = new HashMap<>();

    public AuthService() {

        String encodedUserPass = new BCryptPasswordEncoder().encode("user123");
        String encodedAdminPass = new BCryptPasswordEncoder().encode("admin123");
        String encodedUserPass1 = new BCryptPasswordEncoder().encode("user456");

        users.put("user1", new User("user1", encodedUserPass1, Role.USER));
        users.put("user", new User("user", encodedUserPass, Role.USER));
        users.put("admin",new User("admin", encodedAdminPass, Role.ADMIN));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
