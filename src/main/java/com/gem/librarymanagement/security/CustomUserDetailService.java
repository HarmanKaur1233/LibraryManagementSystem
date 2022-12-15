package com.gem.librarymanagement.security;

import com.gem.librarymanagement.entity.User;
import com.gem.librarymanagement.exceptions.ConfigDataResourceNotFoundException;
import com.gem.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ConfigDataResourceNotFoundException("User", "email", username));
        return user;
    }
}
