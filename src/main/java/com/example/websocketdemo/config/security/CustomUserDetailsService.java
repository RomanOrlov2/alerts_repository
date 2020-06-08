package com.example.websocketdemo.config.security;

import com.example.websocketdemo.domain.ApplicationUser;
import com.example.websocketdemo.domain.Role;
import com.example.websocketdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ApplicationUser applicationUser = userRepository.findByUsername(username);
            if (applicationUser != null)
                return new User(applicationUser.getUsername(), applicationUser.getPassword(), getAuthorities(applicationUser));
        } catch (Exception ex) {
            log.error("Exception in CustomUserDetailsService: " + ex);
        }
        return null;
    }

    private Collection<GrantedAuthority> getAuthorities(ApplicationUser applicationUser) {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : applicationUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}