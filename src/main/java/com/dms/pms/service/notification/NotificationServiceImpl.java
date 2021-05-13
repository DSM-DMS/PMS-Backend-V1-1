package com.dms.pms.service.notification;

import com.dms.pms.payload.request.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Map<String, List<String>> tokens = new HashMap<>();

    public void addToken(NotificationRequest request) {
        List<String> tokenList =  tokens.get(request.getEmail());
        if (tokenList == null) {

            tokenList = new ArrayList<>();
            tokenList.add(request.getToken());
        }

        tokenList.add(request.getToken());

        tokens.put(request.getEmail(), tokenList);
    }

    public List<String> getToken(String email) {
        return tokens.get(email);
    }
}
