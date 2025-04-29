package com.rdruzhchenko.fsjutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rdruzhchenko.fsjutils.json.FSJsonUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the multi-threaded behavior of FSJsonUtils class.
 */
@DisplayName("FSJsonUtils Multi-Threaded")
class FSJsonUtilsMultiThreadedTest {

    // Simple test class for JSON serialization/deserialization
    public static class TestPerson {
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

    @AfterEach
    void resetObjectMapper() {
        // Reset the ObjectMapper after each test to ensure a clean state
        FSJsonUtils.resetObjectMapper();
    }

    @Test
    @DisplayName("Should handle concurrent ObjectMapper configuration")
    void shouldHandleConcurrentObjectMapperConfiguration() throws InterruptedException {
        // Given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicBoolean failureDetected = new AtomicBoolean(false);

        // When
        for (int i = 0; i < threadCount; i++) {
            final boolean prettyPrint = i % 2 == 0;
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready

                    // Configure the ObjectMapper
                    FSJsonUtils.configureObjectMapper(false, prettyPrint);

                    // Test that the mapper still works regardless of configuration
                    TestPerson person = new TestPerson("Test", 25);
                    String json = FSJsonUtils.mapToJson(person);
                    TestPerson parsed = FSJsonUtils.mapFromJson(json, TestPerson.class);

                    if (!person.equals(parsed)) {
                        failureDetected.set(true);
                    }
                } catch (Exception e) {
                    failureDetected.set(true);
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        finishLatch.await(5, TimeUnit.SECONDS); // Wait for all threads to finish
        executorService.shutdown();

        // Then
        assertFalse(failureDetected.get(), "Configuration should be thread-safe and not affect functionality");

        // Verify that the ObjectMapper is still in a valid state after all operations
        TestPerson person = new TestPerson("Final Test", 30);
        String json = FSJsonUtils.mapToJson(person);
        TestPerson parsed = FSJsonUtils.mapFromJson(json, TestPerson.class);
        assertEquals(person.name, parsed.name);
        assertEquals(person.age, parsed.age);
    }

    @Test
    @DisplayName("Should handle concurrent ObjectMapper reset")
    void shouldHandleConcurrentObjectMapperReset() throws InterruptedException {
        // Given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicBoolean failureDetected = new AtomicBoolean(false);

        // When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready
                    FSJsonUtils.resetObjectMapper();

                    // Verify we can still use the mapper after reset
                    TestPerson person = new TestPerson("Test", 25);
                    String json = FSJsonUtils.mapToJson(person);
                    TestPerson parsed = FSJsonUtils.mapFromJson(json, TestPerson.class);

                    if (!person.equals(parsed)) {
                        failureDetected.set(true);
                    }
                } catch (Exception e) {
                    failureDetected.set(true);
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        finishLatch.await(5, TimeUnit.SECONDS); // Wait for all threads to finish
        executorService.shutdown();

        // Then
        assertFalse(failureDetected.get(), "Reset should be thread-safe");
    }

    @Test
    @DisplayName("Should handle concurrent serialization and deserialization")
    void shouldHandleConcurrentSerializationAndDeserialization() throws InterruptedException {
        // Given
        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicBoolean failureDetected = new AtomicBoolean(false);
        List<TestPerson> testPersons = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            testPersons.add(new TestPerson("Person " + i, 20 + i));
        }

        // When
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready

                    TestPerson person = testPersons.get(index);
                    String json = FSJsonUtils.mapToJson(person);
                    TestPerson parsed = FSJsonUtils.mapFromJson(json, TestPerson.class);

                    if (!person.equals(parsed)) {
                        failureDetected.set(true);
                    }
                } catch (Exception e) {
                    failureDetected.set(true);
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        finishLatch.await(5, TimeUnit.SECONDS); // Wait for all threads to finish
        executorService.shutdown();

        // Then
        assertFalse(failureDetected.get(), "Serialization and deserialization should be thread-safe");
    }

    @Test
    @DisplayName("Should handle concurrent setObjectMapper calls")
    void shouldHandleConcurrentSetObjectMapperCalls() throws InterruptedException {
        // Given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicBoolean failureDetected = new AtomicBoolean(false);
        AtomicInteger successfulSets = new AtomicInteger(0);

        // When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready

                    ObjectMapper customMapper = new ObjectMapper();
                    FSJsonUtils.setObjectMapper(customMapper);

                    // Verify the mapper was set correctly
                    if (FSJsonUtils.getObjectMapper() == customMapper) {
                        successfulSets.incrementAndGet();
                    }

                    // Test that the mapper works
                    TestPerson person = new TestPerson("Test", 25);
                    String json = FSJsonUtils.mapToJson(person);
                    TestPerson parsed = FSJsonUtils.mapFromJson(json, TestPerson.class);

                    if (!person.equals(parsed)) {
                        failureDetected.set(true);
                    }
                } catch (Exception e) {
                    failureDetected.set(true);
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        finishLatch.await(5, TimeUnit.SECONDS); // Wait for all threads to finish
        executorService.shutdown();

        // Then
        assertFalse(failureDetected.get(), "Setting ObjectMapper should be thread-safe");
        // At least one thread should have successfully set the mapper
        assertTrue(successfulSets.get() > 0, "At least one thread should have successfully set the mapper");
    }

    @Test
    @DisplayName("Should handle concurrent array deserialization")
    void shouldHandleConcurrentArrayDeserialization() throws InterruptedException {
        // Given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicBoolean failureDetected = new AtomicBoolean(false);

        String jsonArray = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]";

        // When
        for (int i = 0; i < threadCount; i++) {
            final boolean useJsonToList = i % 2 == 0;
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready

                    List<TestPerson> persons;
                    if (useJsonToList) {
                        persons = FSJsonUtils.jsonToList(jsonArray, TestPerson.class);
                    } else {
                        persons = FSJsonUtils.mapFromJsonArray(jsonArray, TestPerson.class);
                    }

                    if (persons.size() != 2 || 
                        !persons.get(0).name.equals("John") || 
                        persons.get(0).age != 30 ||
                        !persons.get(1).name.equals("Jane") || 
                        persons.get(1).age != 25) {
                        failureDetected.set(true);
                    }
                } catch (Exception e) {
                    failureDetected.set(true);
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        finishLatch.await(5, TimeUnit.SECONDS); // Wait for all threads to finish
        executorService.shutdown();

        // Then
        assertFalse(failureDetected.get(), "Array deserialization should be thread-safe");
    }

    @Test
    @DisplayName("Should handle high concurrency with mixed operations")
    void shouldHandleHighConcurrencyWithMixedOperations() throws InterruptedException {
        // Given
        int threadCount = 50; // Higher thread count for stress testing
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicBoolean failureDetected = new AtomicBoolean(false);

        String jsonArray = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]";
        TestPerson testPerson = new TestPerson("Test Person", 42);

        // When
        for (int i = 0; i < threadCount; i++) {
            final int operationType = i % 5; // 5 different operation types
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready

                    switch (operationType) {
                        case 0: // Configure ObjectMapper
                            FSJsonUtils.configureObjectMapper(false, true);
                            break;
                        case 1: // Reset ObjectMapper
                            FSJsonUtils.resetObjectMapper();
                            break;
                        case 2: // Serialize
                            String json = FSJsonUtils.mapToJson(testPerson);
                            if (!json.contains("Test Person") || !json.contains("42")) {
                                failureDetected.set(true);
                            }
                            break;
                        case 3: // Deserialize
                            String personJson = "{\"name\":\"Test Person\",\"age\":42}";
                            TestPerson parsed = FSJsonUtils.mapFromJson(personJson, TestPerson.class);
                            if (!parsed.name.equals("Test Person") || parsed.age != 42) {
                                failureDetected.set(true);
                            }
                            break;
                        case 4: // Array deserialization
                            List<TestPerson> persons = FSJsonUtils.jsonToList(jsonArray, TestPerson.class);
                            if (persons.size() != 2) {
                                failureDetected.set(true);
                            }
                            break;
                    }
                } catch (Exception e) {
                    failureDetected.set(true);
                    e.printStackTrace();
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        finishLatch.await(10, TimeUnit.SECONDS); // Wait for all threads to finish, longer timeout for more threads
        executorService.shutdown();

        // Then
        assertFalse(failureDetected.get(), "Mixed operations should be thread-safe under high concurrency");
    }
}
