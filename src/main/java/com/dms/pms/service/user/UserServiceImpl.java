package com.dms.pms.service.user;

import com.dms.pms.entity.dms.point.history.PointHistoryRepository;
import com.dms.pms.entity.dms.point.item.ItemRepository;
import com.dms.pms.entity.dms.stay.StayRepository;
import com.dms.pms.payload.request.StudentDeleteRequest;
import com.dms.pms.entity.dms.student.PointRepository;
import com.dms.pms.entity.pms.meal.MealApplyRepository;
import com.dms.pms.entity.pms.outing.OutingRepository;
import com.dms.pms.entity.pms.student.StudentRepository;
import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.*;
import com.dms.pms.payload.request.ChangeNameRequest;
import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.payload.request.StudentAdditionRequest;
import com.dms.pms.payload.response.StudentInformationResponse;
import com.dms.pms.payload.response.StudentListResponse;
import com.dms.pms.payload.response.StudentOutingListResponse;
import com.dms.pms.payload.response.StudentPointListResponse;
import com.dms.pms.security.auth.AuthenticationFacade;
import com.dms.pms.security.auth.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PointHistoryRepository pointHistoryRepository;
    private final ItemRepository itemRepository;
    private final OutingRepository outingRepository;

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
                        .authProvider(AuthProvider.LOCAL)
                        .build()
        );
    }

    @Override
    public void changeName(ChangeNameRequest request) {
        userRepository.findById(authenticationFacade.getUserEmail())
                .map(user -> user.changeName(request.getName()))
                .map(userRepository::save)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void addStudent(StudentAdditionRequest request) {
        userRepository.findById(authenticationFacade.getUserEmail())
                .map(user -> studentRepository.findById(request.getNumber())
                        .map(student -> userRepository.save(user.addStudent(student)))
                        .orElseThrow(StudentNotFoundException::new))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
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
                    response.setName(user.getName());

                    user.getStudents()
                            .forEach(student -> response.addStudent(new StudentListResponse.Student(student.getStudentNumber(), student.getName())));

                    return response;
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public StudentPointListResponse getStudentPoint(Integer number) {
        return studentRepository.findByStudentNumber(number)
                .filter(student -> {
                    User user = userRepository.findById(authenticationFacade.getUserEmail())
                            .orElseThrow(UserNotFoundException::new);

                    return student.getUsers().contains(user);
                })
                .map(student -> {
                    StudentPointListResponse response = new StudentPointListResponse();

                    pointHistoryRepository.findAllByStudentIdOrderByDateDesc(student.getStudentId())
                            .forEach(pointHistory -> {
                                itemRepository.findById(pointHistory.getPointId())
                                        .map(item -> {
                                            response.addPoint(new StudentPointListResponse.Point(item.getReason(), item.getPoint(), pointHistory.getDate(), item.isType()));
                                            return item;
                                        })
                                        .orElseThrow(ItemNotFoundException::new);
                            });
                    return response;
                })
                .orElseThrow(UserHasNotStudentException::new);
    }

    @Override
    @Transactional
    public StudentOutingListResponse getStudentOuting(Integer number) {
        return studentRepository.findByStudentNumber(number)
                .filter(student -> {
                    User user = userRepository.findById(authenticationFacade.getUserEmail())
                            .orElseThrow(UserNotFoundException::new);

                    return student.getUsers().contains(user);
                })
                .map(student -> {
                    StudentOutingListResponse response = new StudentOutingListResponse();

                    outingRepository.findAllByStudentNumber(student.getStudentNumber())
                            .forEach(outing -> {
                                response.addOuting(new StudentOutingListResponse.Outing(outing.getDate(), outing.getReason(), outing.getPlace(), outing.getType()));
                            });

                    return response;
                })
                .orElseThrow(UserHasNotStudentException::new);
    }

    @Override
    public void deleteStudent(StudentDeleteRequest request) {
        studentRepository.findByStudentNumber(request.getNumber())
                .map(student -> {
                    User user = userRepository.findById(authenticationFacade.getUserEmail())
                            .orElseThrow(UserNotFoundException::new);

                    student.getUsers().remove(user);
                    user.getStudents().remove(student);
                    studentRepository.save(student);
                    userRepository.save(user);
                    return student;
                })
                .orElseThrow(StudentNotFoundException::new);
    }
}