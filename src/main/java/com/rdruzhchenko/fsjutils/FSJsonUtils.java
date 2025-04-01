package com.rdruzhchenko.fsjutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FSJsonUtils {

    public static <T> String mapToJson(T restModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(restModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T mapFromJson(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> mapFromJsonArray(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        List modelList = mapFromJson(json, ArrayList.class);
        var list = modelList.stream().map(o -> {
            try {
                if (o instanceof HashMap) {
                    return hashMapToRest((HashMap) o, clazz);
                }
                if (o instanceof List) {
                    return o;
                }
                return o;
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return list;
    }

    // tw
    public static <T> T hashMapToRest(HashMap map, Class<T> clazz)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        T restModel = clazz.newInstance();

        var keys = map.keySet().toArray();
        for (Object key : keys) {
            Field field = restModel.getClass().getDeclaredField(key.toString());
            field.set(restModel, map.get(key.toString()));
        }

        return restModel;
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var typeFactory = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clazz);
            return objectMapper.readValue(json, typeFactory);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
