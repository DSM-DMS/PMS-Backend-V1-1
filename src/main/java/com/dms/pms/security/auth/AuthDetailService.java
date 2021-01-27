package com.dms.pms.security.auth;

import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(user -> new AuthDetails(user, user.getRoleType()))
                .orElseThrow(UserNotFoundException::new);
    }
}
