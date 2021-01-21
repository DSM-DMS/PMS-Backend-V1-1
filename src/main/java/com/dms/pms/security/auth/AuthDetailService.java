package com.dms.pms.security.auth;

import com.dms.pms.entity.pms.user.ParentRepository;
import com.dms.pms.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDetailService implements UserDetailsService {
    private final ParentRepository parentRepository;

    @Override
    public AuthDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return parentRepository.findById(username)
                .map(user -> new AuthDetails(user, user.getRoleType()))
                .orElseThrow(UserNotFoundException::new);
    }
}
