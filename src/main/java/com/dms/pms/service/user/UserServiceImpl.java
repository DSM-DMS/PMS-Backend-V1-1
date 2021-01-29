package com.dms.pms.service.user;

import com.dms.pms.entity.dms.stay.StayRepository;
import com.dms.pms.entity.dms.student.PointRepository;
import com.dms.pms.entity.pms.meal.MealApplyRepository;
import com.dms.pms.entity.pms.student.StudentRepository;
import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.StudentNotFoundException;
import com.dms.pms.exception.UserAlreadyExistsException;
import com.dms.pms.exception.UserHasNotStudentException;
import com.dms.pms.exception.UserNotFoundException;
import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.payload.request.StudentAdditionRequest;
import com.dms.pms.payload.response.StudentInformationResponse;
import com.dms.pms.payload.response.StudentListResponse;
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
    private final PointRepository pointRepository;
    private final StayRepository stayRepository;
    private final MealApplyRepository mealApplyRepository;

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

    @Override
    public StudentInformationResponse getStudentInfo(Integer number) {
        return studentRepository.findByStudentNumber(number)
                .filter(student -> {
                    User user = userRepository.findById(authenticationFacade.getUserEmail()).orElseThrow(UserNotFoundException::new);
                    return student.getUsers().contains(user);
                })
                .map(student -> {
                    StudentInformationResponse response = new StudentInformationResponse();
                    pointRepository.findById(student.getStudentId())
                            .map(point -> {
                                response.setBonusPoint(point.getGoodPoint());
                                response.setMinusPoint(point.getBadPoint());

                                return point;
                            })
                            .orElseThrow(StudentNotFoundException::new);
                    stayRepository.findById(student.getStudentId())
                            .map(stay -> {
                                response.setStay(stay.getValue());
                                return stay;
                            })
                            .orElseThrow(StudentNotFoundException::new);
                    mealApplyRepository.findById(student.getStudentId())
                            .map(mealApply -> {
                                response.setMealApplied(mealApply.getStatus());
                                return mealApply;
                            })
                            .orElseThrow(StudentNotFoundException::new);

                    return response;
                })
                .orElseThrow(UserHasNotStudentException::new);
    }

    @Override
    public StudentListResponse getStudentList() {
        return userRepository.findById(authenticationFacade.getUserEmail())
                .map(user -> {
                    StudentListResponse response = new StudentListResponse();
                    user.getStudents()
                            .forEach(student -> response.addStudent(new StudentListResponse.Student(student.getStudentNumber(), student.getName())));

                    return response;
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
