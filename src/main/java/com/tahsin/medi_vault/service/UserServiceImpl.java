package com.tahsin.medi_vault.service;

import com.tahsin.medi_vault.entity.User;
import com.tahsin.medi_vault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return userRepository.findByEmail(((UserDetails) principal).getUsername()).orElseThrow(
                        () -> new UsernameNotFoundException(STR."User not found with email: \{((UserDetails) principal).getUsername()}")
                );
            } else {

                return userRepository.findByEmail(principal.toString()).orElseThrow(
                        () -> new UsernameNotFoundException(STR."User not found with email: \{principal.toString()}")
                );
            }
        }
        return null;
    }
}

