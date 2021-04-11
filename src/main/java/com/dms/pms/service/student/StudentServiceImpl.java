package com.dms.pms.service.student;

import com.dms.pms.entity.pms.outing.Outing;
import com.dms.pms.entity.pms.outing.OutingRepository;
import com.dms.pms.payload.request.AddOutingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final OutingRepository outingRepository;

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
    }
}
