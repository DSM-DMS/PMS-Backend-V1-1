package com.dms.pms.service.notification;

import com.dms.pms.payload.request.NotificationRequest;

import java.util.List;

public interface NotificationService {
    public void addToken(NotificationRequest request);
    public List<String> getToken(String email);
}
