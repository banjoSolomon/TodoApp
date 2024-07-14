package org.solomon11.security.services;

import lombok.AllArgsConstructor;
import org.solomon11.models.User;
import org.solomon11.security.models.SecureUser;
import org.solomon11.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userService.getUserByUsername(username);
            return new SecureUser(user);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
