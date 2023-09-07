package com.dmdev.validator;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CreateUserValidatorTest {

    private final CreateUserValidator validator = CreateUserValidator.getInstance();

    @Test
    void shouldPassValidation() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("123@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();

        ValidationResult actualResult = validator.validate(dto);

        Assertions.assertFalse(actualResult.hasErrors());
    }

    @Test
    void invalidBirthday() {

        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("123@gmail.com")
                .password("123")
                .birthday("2000-01-01 12:23")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();

        ValidationResult actualResult = validator.validate(dto);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors()).hasSize(1);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.birthday");
    }

    @Test
    void invalidGender() {

        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("123@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender("fake")
                .build();

        ValidationResult actualResult = validator.validate(dto);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors()).hasSize(1);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.gender");
    }

    @Test
    void invalidRole() {

        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("123@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role("fake")
                .gender(Gender.MALE.name())
                .build();

        ValidationResult actualResult = validator.validate(dto);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors()).hasSize(1);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.role");
    }

    @Test
    void invalidRoleGenderBirthday() {

        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("123@gmail.com")
                .password("123")
                .birthday("2000-01-01 12-23")
                .role("fake_role")
                .gender("fake_gender")
                .build();

        ValidationResult actualResult = validator.validate(dto);

        org.assertj.core.api.Assertions.
                assertThat(actualResult.getErrors()).hasSize(3);

        List<String> errorCodes = actualResult.getErrors().stream()
                .map(Error::getCode).toList();

        org.assertj.core.api.Assertions.
                assertThat(errorCodes).contains("invalid.role", "invalid.gender", "invalid.birthday");
    }

}