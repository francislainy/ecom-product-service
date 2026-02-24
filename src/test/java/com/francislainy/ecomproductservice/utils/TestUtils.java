package com.francislainy.ecomproductservice.utils;

import tools.jackson.databind.json.JsonMapper;

public class TestUtils {
    public static String toJson(Object object) {
        JsonMapper jm = JsonMapper.builder().build();
        try {
            return jm.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object fromJson(String json, Class<?> clazz) {
        JsonMapper jm = JsonMapper.builder().build();
        try {
            return jm.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
