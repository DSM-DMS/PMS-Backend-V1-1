package com.dms.pms.utils.api;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @see feign.codec.ErrorDecoder
 * Feign Client 요청 시 발생하는 오류를 핸들링하는 클래스
 */
@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    private final StringDecoder stringDecoder = new StringDecoder();

    /**
     *
     * @param methodKey method key
     * @param response response value
     * @return FeignException.FeignClientException
     *
     * Error 발생 시 Exception 만들어주는 함수
     */
    @Override
    public Exception decode(final String methodKey, Response response) {
        String message = "Server failed to request oauth server.";

        if (response.body() != null) {
            try {
                message = stringDecoder.decode(response, String.class).toString();
            } catch (IOException e) {
                log.error(methodKey + "Error Deserializing response body from failed feign request response.", e);
            }
        }

        return FeignException.FeignClientException.errorStatus(methodKey, response);
    }
}
