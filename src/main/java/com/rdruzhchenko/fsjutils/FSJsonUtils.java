package com.rdruzhchenko.fsjutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdruzhchenko.fsjutils.exception.FSJsonException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Utility class providing JSON serialization and deserialization operations.
 * This class contains methods for converting objects to JSON strings,
 * parsing JSON strings into objects, and handling JSON arrays.
 */
public class FSJsonUtils {

    /**
     * Converts an object to a JSON string.
     *
     * @param <T> The type of the object to convert
     * @param restModel The object to convert to JSON
     * @return The JSON string representation of the object
     * @throws FSJsonException if serialization fails
     */
    public static <T> String mapToJson(T restModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(restModel);
        } catch (JsonProcessingException e) {
            throw new FSJsonException("Failed to serialize object to JSON", e);
        }
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param <T> The type of the object to create
     * @param json The JSON string to parse
     * @param clazz The class of the object to create
     * @return An object of the specified class created from the JSON string
     * @throws FSJsonException if deserialization fails
     */
    public static <T> T mapFromJson(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new FSJsonException("Failed to deserialize JSON to object of type " + clazz.getName(), e);
        }
    }

    /**
     * Converts a JSON array string to a list of objects of the specified class.
     *
     * @param <T> The type of objects in the list
     * @param json The JSON array string to parse
     * @param clazz The class of the objects to create
     * @return A list of objects of the specified class created from the JSON array
     * @throws FSJsonException if deserialization or conversion fails
     */
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
                throw new FSJsonException("Failed to convert HashMap to object of type " + clazz.getName(), e);
            }
        }).toList();

        return list;
    }

    /**
     * Converts a HashMap to an object of the specified class.
     * Each key in the HashMap is matched to a field name in the class.
     *
     * @param <T> The type of the object to create
     * @param map The HashMap containing the field values
     * @param clazz The class of the object to create
     * @return An object of the specified class with fields set from the HashMap
     * @throws NoSuchFieldException if a field in the class matching a key in the HashMap is not found
     * @throws IllegalAccessException if a field cannot be accessed
     * @throws InstantiationException if an instance of the class cannot be created
     */
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
            throw new FSJsonException("Failed to deserialize JSON to list of " + clazz.getName(), e);
        }
    }
}
