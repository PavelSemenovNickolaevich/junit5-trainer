package com.dmdev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PropertiesUtilTest {

   @ParameterizedTest
   @MethodSource("getPropertyArguments")
   void checkGet(String key, String expectedValue) {
       String actualResult = PropertiesUtil.get(key);

       Assertions.assertEquals(expectedValue, actualResult);
   }

    private static Stream<Arguments> getPropertyArguments() {
        return Stream.of(
                arguments("db.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                arguments("db.use", "sa"),
                arguments("db.password", "")
        );
    }

}