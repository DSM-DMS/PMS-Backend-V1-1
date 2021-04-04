package com.dms.pms.service.user;

import com.dms.pms.payload.request.ChangeNameRequest;
import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.payload.request.StudentAdditionRequest;
import com.dms.pms.payload.request.StudentDeleteRequest;
import com.dms.pms.payload.response.StudentInformationResponse;
import com.dms.pms.payload.response.StudentListResponse;
import com.dms.pms.payload.response.StudentOutingListResponse;
import com.dms.pms.payload.response.StudentPointListResponse;

public interface UserService {
    public void register(RegisterRequest request);
    public void changeName(ChangeNameRequest request);
    public void addStudent(StudentAdditionRequest request);
    public void deleteStudent(StudentDeleteRequest request);
    public StudentInformationResponse getStudentInfo(Integer number);
    public StudentListResponse getStudentList();
    public StudentPointListResponse getStudentPoint(Integer number);
    public StudentOutingListResponse getStudentOuting(Integer number);
}
