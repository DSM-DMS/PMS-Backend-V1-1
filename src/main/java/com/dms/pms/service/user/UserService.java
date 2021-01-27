package com.dms.pms.service.user;

import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.payload.request.StudentAdditionRequest;

public interface UserService {
    public void register(RegisterRequest request);
    public void addStudent(StudentAdditionRequest request);
}
