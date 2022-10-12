package com.pyanik.mathcoursestock.Instructor.validations;

import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class InstructorValidationTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "James";
    private static final String EMAIL = "john.james@mail.com";
    private static final String BIO = "Sample bio";

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @Test
    void shouldDetectEmptyFirstName() {
        // given
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(null, LAST_NAME, EMAIL, BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(2);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("first name must not be empty")));
    }

    @Test
    void shouldDetectTooLongFirstName() {
        // given
        String firstName = "John".repeat(6);
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(firstName, LAST_NAME, EMAIL, BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(1);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("first name must not be longer than 20 letters")));
    }

    @Test
    void shouldDetectFirstNameWithInvalidCharacters() {
        // given
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO("John#$@", LAST_NAME, EMAIL, BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(1);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("first name must contains only letters")));
    }

    @Test
    void shouldDetectEmptyLastName() {
        // given
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME,null, EMAIL, BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(2);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("last name must not be empty")));
    }

    @Test
    void shouldDetectTooLongLastName() {
        // given
        String lastName = "James".repeat(5);
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME, lastName, EMAIL, BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(1);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("last name must not be longer than 20 letters")));
    }

    @Test
    void shouldDetectLastNameWithInvalidCharacters() {
        // given
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME, "1James", EMAIL, BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(1);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("last name must contains only letters")));
    }

    @Test
    void shouldDetectInvalidEmail() {
        // given
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME, LAST_NAME, "johnjames.mail.com", BIO);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(1);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("email should be a valid email")));
    }

    @Test
    void shouldDetectTooLongBio() {
        // given
        String bio = "myBio".repeat(26);
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME, LAST_NAME, EMAIL, bio);

        // when
        Set<ConstraintViolation<InputInstructorDTO>> violations = validator.validate(inputInstructorDTO);

        // then
        assertThat(violations).hasSize(1);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(m -> m.equals("bio must not be longer than 100 characters")));
    }

}
