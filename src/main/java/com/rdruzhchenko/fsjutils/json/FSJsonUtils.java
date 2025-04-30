package com.rdruzhchenko.fsjutils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rdruzhchenko.fsjutils.exception.FSJsonException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Utility class providing JSON serialization and deserialization operations.
 * This class contains methods for converting objects to JSON strings,
 * parsing JSON strings into objects, and handling JSON arrays.
 * 
 * The class uses a configurable ObjectMapper instance for all operations.
 * This class is thread-safe and can be safely used in multi-threaded environments.
 */
public class FSJsonUtils {

    private static volatile ObjectMapper objectMapper = createDefaultObjectMapper();
    private static final Object lock = new Object();

    /**
     * Creates a default ObjectMapper with standard configuration.
     *
     * @return A new ObjectMapper instance with default configuration
     */
    private static ObjectMapper createDefaultObjectMapper() {
        return new ObjectMapper();
    }

    /**
     * Gets the current ObjectMapper instance used by this utility class.
     *
     * @return The current ObjectMapper instance
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Sets a custom ObjectMapper to be used by this utility class.
     * This method is thread-safe.
     *
     * @param mapper The ObjectMapper instance to use
     */
    public static void setObjectMapper(ObjectMapper mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("ObjectMapper cannot be null");
        }
        synchronized (lock) {
            objectMapper = mapper;
        }
    }

    /**
     * Configures the ObjectMapper with common settings.
     * This method is thread-safe.
     *
     * @param failOnUnknownProperties Whether to fail on unknown properties during deserialization
     * @param prettyPrint Whether to enable pretty printing for serialization
     */
    public static void configureObjectMapper(boolean failOnUnknownProperties, boolean prettyPrint) {
        synchronized (lock) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
            if (prettyPrint) {
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            } else {
                objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
            }
        }
    }

    /**
     * Resets the ObjectMapper to its default configuration.
     * This method is thread-safe.
     */
    public static void resetObjectMapper() {
        synchronized (lock) {
            objectMapper = createDefaultObjectMapper();
        }
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param <T> The type of the object to convert
     * @param restModel The object to convert to JSON
     * @return The JSON string representation of the object
     * @throws FSJsonException if serialization fails
     */
    public static <T> String mapToJson(T restModel) {
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
     * @throws IllegalArgumentException if json is null
     */
    public static <T> T mapFromJson(String json, Class<T> clazz) {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }
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
     * @throws IllegalArgumentException if json is null
     */
    public static <T> List<T> mapFromJsonArray(String json, Class<T> clazz) {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }
        List<?> modelList = mapFromJson(json, ArrayList.class);
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) modelList.stream().map(o -> {
            try {
                if (o instanceof HashMap<?, ?>) {
                    return hashMapToRest((HashMap<?, ?>) o, clazz);
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
    public static <T> T hashMapToRest(HashMap<?, ?> map, Class<T> clazz)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        try {
            T restModel = clazz.getDeclaredConstructor().newInstance();

            var keys = map.keySet().toArray();
            for (Object key : keys) {
                Field field = restModel.getClass().getDeclaredField(key.toString());
                field.set(restModel, map.get(key.toString()));
            }

            return restModel;
        } catch (NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            // Wrap constructor-related exceptions in InstantiationException for backward compatibility
            throw new InstantiationException("Failed to instantiate class: " + e.getMessage());
        }
    }

    /**
     * Converts a JSON array string directly to a list of objects of the specified class.
     * This method uses Jackson's TypeFactory for more efficient list deserialization.
     *
     * @param <T> The type of objects in the list
     * @param json The JSON array string to parse
     * @param clazz The class of the objects to create
     * @return A list of objects of the specified class created from the JSON array
     * @throws FSJsonException if deserialization fails
     * @throws IllegalArgumentException if json is null
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }
        try {
            var typeFactory = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clazz);
            return objectMapper.readValue(json, typeFactory);
        } catch (JsonProcessingException e) {
            throw new FSJsonException("Failed to deserialize JSON to list of " + clazz.getName(), e);
        }
    }
}
