package com.dms.pms.service.notification;

import com.dms.pms.payload.request.NotificationRequest;
import com.dms.pms.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final Map<String, List<String>> tokens = new HashMap<>();

    private final AuthenticationFacade facade;

    public void addToken(NotificationRequest request) {
        List<String> tokenList = tokens.get(request.getToken());
        if (tokenList == null) {

            tokenList = new ArrayList<>();
            tokenList.add(request.getToken());
        }

        tokenList.add(request.getToken());

        tokens.put(facade.getUserEmail(), tokenList);
    }

    public List<String> getToken(String email) {
        List<String> tokenList = tokens.get(email);
        return tokenList != null ? tokenList : new ArrayList<>();
    }
}
