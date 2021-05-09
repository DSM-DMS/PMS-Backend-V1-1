package com.dms.pms.service.student;

import com.dms.pms.entity.pms.outing.Outing;
import com.dms.pms.entity.pms.outing.OutingRepository;
import com.dms.pms.entity.pms.student.Student;
import com.dms.pms.entity.pms.student.StudentRepository;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.exception.StudentNotFoundException;
import com.dms.pms.payload.request.AddOutingRequest;
import com.dms.pms.service.notification.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final OutingRepository outingRepository;
    private final StudentRepository studentRepository;
    private final NotificationService notificationService;

    @Override
    public void addOuting(AddOutingRequest request) {
        outingRepository.save(
                Outing.builder()
                .studentNumber(request.getNumber())
                .reason(request.getReason())
                .place(request.getPlace())
                .date(LocalDate.now())
                .type(request.getType())
                .build()
        );

        Student student = studentRepository.findByStudentNumber(request.getNumber())
                .orElseThrow(StudentNotFoundException::new);

        Set<User> users = student.getUsers();

        users.forEach(receiver -> notificationService.getToken(receiver.getEmail()).forEach(user -> {
            Message message = Message.builder()
                    .setToken("")
                    .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                            .setNotification(
                                    new WebpushNotification(student.getName() + "님이 외출하였습니다.",
                                            "장소: " + request.getPlace()))
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("Sent message: {}", response);
        }));
    }
}
