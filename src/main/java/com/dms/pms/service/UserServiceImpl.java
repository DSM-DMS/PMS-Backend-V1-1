package com.dms.pms.service;

import com.dms.pms.entity.pms.user.Parent;
import com.dms.pms.entity.pms.user.ParentRepository;
import com.dms.pms.exception.UserAlreadyExistsException;
import com.dms.pms.payload.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        parentRepository.findById(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

        parentRepository.save(
                Parent.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build()
        );
    }
}
