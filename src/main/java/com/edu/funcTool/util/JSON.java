package com.edu.funcTool.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;

public class JSON {

    // 建造者模式
    private static final ObjectMapper MAPPER = Jackson2ObjectMapperBuilder
            .json().build();

    // 私有构造器
    private JSON() {
    }

    // 获取 ObjectMapper 对象
    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    // 序列化
    public static String toJson(Object obj) {
        if(obj == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonException("Json convert error", e);
        }
    }
    // 反序列化
    public static <T> T fromJson(String json, Class<T> clazz) {
        if(!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonException("Json read value error", e);
        }
    }

    // 反序列化
    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        if(!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new JsonException("Json read value error", e);
        }
    }

    static class JsonException extends RuntimeException {
        /**
         * JSON异常
         */
        public JsonException(String message) {
            super(message);
        }
        public JsonException(String message, Throwable cause) {
            super(message, cause);
        }
        public JsonException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
            super(message, throwable, enableSuppression, writableStackTrace);
        }
    }
}
