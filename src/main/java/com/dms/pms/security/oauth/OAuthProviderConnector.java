package com.dms.pms.security.oauth;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.exception.ProviderUserInvalidException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuthProviderConnector implements OAuthProviderConnect {

    @Override
    public <T> Optional<T> getUserInfo(String token, AuthProvider type) {
        Map<String, String> requestHeader = new HashMap<>();
        int statusCode;

        requestHeader.put("Authorization", "Bearer " + token);
        HttpURLConnection con = connect(type.getProviderUri());

        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeader.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            statusCode = con.getResponseCode();
        } catch(IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }

        if (statusCode != HttpURLConnection.HTTP_OK) {
            return Optional.empty();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
