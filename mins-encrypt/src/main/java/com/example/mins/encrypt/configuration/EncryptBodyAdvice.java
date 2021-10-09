package com.example.mins.encrypt.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestControllerAdvice(basePackages = "com.example.mins.encrypt")
@Slf4j
public class EncryptBodyAdvice implements RequestBodyAdvice, ResponseBodyAdvice<Object> {

    /**
     * 消息内容处理判断开关（必须为true才会执行beforeBodyRead和afterBodyRead方法）
     *
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(Encrypt.class) && methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        log.info("beforeBodyRead...");
        Encrypt encrypt = parameter.getParameterAnnotation(Encrypt.class);
        if ("aes".equals(encrypt.encryptMethod())) {
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    String bodyStr = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
                    log.info("解密处理。。。" + bodyStr);
                    return IOUtils.toInputStream(bodyStr, StandardCharsets.UTF_8);
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } else {
            log.warn("解密方法：" + encrypt.encryptMethod());
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("afterBodyRead...{}", body.toString());
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("handleEmptyBody...");
        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("beforeBodyWrite...");
        if (null != body) {
            Encrypt encrypt = Objects.requireNonNull(returnType.getMethod()).getAnnotation(Encrypt.class);
            String str = encrypt.encryptMethod();
            log.info("此处进行加密数据[{}]。。。{}", str, body);
        }
        return body;
    }

}
