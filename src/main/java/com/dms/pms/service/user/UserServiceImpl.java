package com.dms.pms.service.user;

import com.dms.pms.entity.pms.student.StudentRepository;
import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.StudentNotFoundException;
import com.dms.pms.exception.UserAlreadyExistsException;
import com.dms.pms.exception.UserNotFoundException;
import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.payload.request.StudentAdditionRequest;
import com.dms.pms.security.auth.AuthenticationFacade;
import com.dms.pms.security.auth.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public void register(RegisterRequest request) {
        userRepository.findById(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

        userRepository.save(
                User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .roleType(RoleType.USER)
                .authProvider(AuthProvider.local)
                .build()
        );
    }

    @Override
    public void addStudent(StudentAdditionRequest request) {
        userRepository.findById(authenticationFacade.getUserEmail())
                .map(user -> {
                    return studentRepository.findById(request.getNumber())
                            .map(student -> userRepository.save(user.addStudent(student)))
                            .orElseThrow(StudentNotFoundException::new);
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
