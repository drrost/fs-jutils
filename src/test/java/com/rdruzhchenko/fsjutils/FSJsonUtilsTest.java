package com.rdruzhchenko.fsjutils;

import com.rdruzhchenko.fsjutils.exception.FSJsonException;
import com.rdruzhchenko.fsjutils.json.FSJsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FSJsonUtils class.
 */
@DisplayName("FSJsonUtils")
class FSJsonUtilsTest {

    // Simple test class for JSON serialization/deserialization
    static class TestPerson {
        public String name;
        public int age;

        // Default constructor required for Jackson
        public TestPerson() {
        }

        public TestPerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TestPerson that = (TestPerson) obj;
            return age == that.age && name.equals(that.name);
        }
    }

    @Nested
    @DisplayName("mapToJson")
    class MapToJson {

        @Test
        @DisplayName("Should convert object to JSON string")
        void shouldConvertObjectToJsonString() {
            // Given
            TestPerson person = new TestPerson("John Doe", 30);

            // When
            String json = FSJsonUtils.mapToJson(person);

            // Then
            assertTrue(json.contains("\"name\":\"John Doe\""));
            assertTrue(json.contains("\"age\":30"));
        }

        @Test
        @DisplayName("Should handle null object")
        void shouldHandleNullObject() {
            // When
            String json = FSJsonUtils.mapToJson(null);

            // Then
            assertEquals("null", json);
        }
    }

    @Nested
    @DisplayName("mapFromJson")
    class MapFromJson {

        @Test
        @DisplayName("Should convert JSON string to object")
        void shouldConvertJsonStringToObject() {
            // Given
            String json = "{\"name\":\"John Doe\",\"age\":30}";

            // When
            TestPerson person = FSJsonUtils.mapFromJson(json, TestPerson.class);

            // Then
            assertEquals("John Doe", person.name);
            assertEquals(30, person.age);
        }

        @Test
        @DisplayName("Should handle null JSON string")
        void shouldHandleNullJsonString() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> FSJsonUtils.mapFromJson(null, TestPerson.class));
        }

        @Test
        @DisplayName("Should handle empty JSON string")
        void shouldHandleEmptyJsonString() {
            // When & Then
            assertThrows(FSJsonException.class, () -> FSJsonUtils.mapFromJson("", TestPerson.class));
        }

        @Test
        @DisplayName("Should handle invalid JSON string")
        void shouldHandleInvalidJsonString() {
            // Given
            String invalidJson = "{name:John Doe,age:30}"; // Missing quotes

            // When & Then
            assertThrows(FSJsonException.class, () -> FSJsonUtils.mapFromJson(invalidJson, TestPerson.class));
        }
    }

    @Nested
    @DisplayName("mapFromJsonArray")
    class MapFromJsonArray {

        @Test
        @DisplayName("Should convert JSON array to list of objects")
        void shouldConvertJsonArrayToListOfObjects() {
            // Given
            String jsonArray = "[{\"name\":\"John Doe\",\"age\":30},{\"name\":\"Jane Doe\",\"age\":25}]";

            // When
            List<TestPerson> persons = FSJsonUtils.mapFromJsonArray(jsonArray, TestPerson.class);

            // Then
            assertEquals(2, persons.size());
            assertEquals("John Doe", persons.get(0).name);
            assertEquals(30, persons.get(0).age);
            assertEquals("Jane Doe", persons.get(1).name);
            assertEquals(25, persons.get(1).age);
        }

        @Test
        @DisplayName("Should handle empty JSON array")
        void shouldHandleEmptyJsonArray() {
            // Given
            String emptyJsonArray = "[]";

            // When
            List<TestPerson> persons = FSJsonUtils.mapFromJsonArray(emptyJsonArray, TestPerson.class);

            // Then
            assertTrue(persons.isEmpty());
        }

        @Test
        @DisplayName("Should handle null JSON array")
        void shouldHandleNullJsonArray() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> FSJsonUtils.mapFromJsonArray(null, TestPerson.class));
        }

        @Test
        @DisplayName("Should handle invalid JSON array")
        void shouldHandleInvalidJsonArray() {
            // Given
            String invalidJsonArray = "[{name:John Doe,age:30}]"; // Missing quotes

            // When & Then
            assertThrows(FSJsonException.class, () -> FSJsonUtils.mapFromJsonArray(invalidJsonArray, TestPerson.class));
        }
    }

    @Nested
    @DisplayName("hashMapToRest")
    class HashMapToRest {

        @Test
        @DisplayName("Should convert HashMap to object")
        void shouldConvertHashMapToObject() throws Exception {
            // Given
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", "John Doe");
            map.put("age", 30);

            // When
            TestPerson person = FSJsonUtils.hashMapToRest(map, TestPerson.class);

            // Then
            assertEquals("John Doe", person.name);
            assertEquals(30, person.age);
        }

        @Test
        @DisplayName("Should handle null HashMap")
        void shouldHandleNullHashMap() {
            // When & Then
            assertThrows(NullPointerException.class, () -> FSJsonUtils.hashMapToRest(null, TestPerson.class));
        }

        @Test
        @DisplayName("Should handle missing field in class")
        void shouldHandleMissingFieldInClass() {
            // Given
            HashMap<String, Object> map = new HashMap<>();
            map.put("nonExistentField", "value");

            // When & Then
            assertThrows(NoSuchFieldException.class, () -> FSJsonUtils.hashMapToRest(map, TestPerson.class));
        }
    }

    @Nested
    @DisplayName("jsonToList")
    class JsonToList {

        @Test
        @DisplayName("Should convert JSON array directly to list of objects")
        void shouldConvertJsonArrayDirectlyToListOfObjects() {
            // Given
            String jsonArray = "[{\"name\":\"John Doe\",\"age\":30},{\"name\":\"Jane Doe\",\"age\":25}]";

            // When
            List<TestPerson> persons = FSJsonUtils.jsonToList(jsonArray, TestPerson.class);

            // Then
            assertEquals(2, persons.size());
            assertEquals("John Doe", persons.get(0).name);
            assertEquals(30, persons.get(0).age);
            assertEquals("Jane Doe", persons.get(1).name);
            assertEquals(25, persons.get(1).age);
        }

        @Test
        @DisplayName("Should handle empty JSON array")
        void shouldHandleEmptyJsonArray() {
            // Given
            String emptyJsonArray = "[]";

            // When
            List<TestPerson> persons = FSJsonUtils.jsonToList(emptyJsonArray, TestPerson.class);

            // Then
            assertTrue(persons.isEmpty());
        }

        @Test
        @DisplayName("Should handle null JSON array")
        void shouldHandleNullJsonArray() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> FSJsonUtils.jsonToList(null, TestPerson.class));
        }

        @Test
        @DisplayName("Should handle invalid JSON array")
        void shouldHandleInvalidJsonArray() {
            // Given
            String invalidJsonArray = "[{name:John Doe,age:30}]"; // Missing quotes

            // When & Then
            assertThrows(FSJsonException.class, () -> FSJsonUtils.jsonToList(invalidJsonArray, TestPerson.class));
        }
    }
}
