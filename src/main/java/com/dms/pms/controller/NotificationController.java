package com.dms.pms.controller;

import com.dms.pms.payload.request.NotificationRequest;
import com.dms.pms.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping()
    public void addToken(@RequestBody @Valid NotificationRequest request) {
        notificationService.addToken(request);
    }
}
