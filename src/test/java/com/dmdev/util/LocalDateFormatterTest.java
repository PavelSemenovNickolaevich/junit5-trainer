package com.dmdev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class LocalDateFormatterTest {

    @Test
    void format() {
        String date = "2020-11-28";

        LocalDate actualResult = LocalDateFormatter.format(date);

        assertThat(actualResult).isEqualTo(LocalDate.of(2020, 11, 28));
    }

    @Test
    void shouldThrowExceptionIfDateInvalid() {
        String date = "2020-11-28 12:25";

        Assertions.assertThrows(DateTimeParseException.class, () -> LocalDateFormatter.format(date));
    }

    @ParameterizedTest
    @MethodSource("getValidationArgument")
    void isValid(String date, boolean expectedResult) {
        boolean actualResult = LocalDateFormatter.isValid(date);

        Assertions.assertEquals(expectedResult, actualResult);
    }


    private static Stream<Arguments> getValidationArgument() {
        return Stream.of(
                arguments("2020-11-20", true),
                arguments("01-01-2001", false),
                arguments("2020-11-28 12:25", false),
                arguments(null, false)
        );
    }
}