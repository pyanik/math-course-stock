package com.pyanik.mathcoursestock.Instructor.service;

import com.pyanik.mathcoursestock.exception.EntityNotSavedException;
import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.Instructor;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorRepository;
import com.pyanik.mathcoursestock.instructor.service.InstructorModelMapper;
import com.pyanik.mathcoursestock.instructor.service.InstructorService;
import com.pyanik.mathcoursestock.instructor.service.InstructorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InstructorServiceImplTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "James";
    private static final String EMAIL = "john.james@mail.com";
    private static final String BIO = "Sample bio";

    private InstructorService instructorService;

    @Mock
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorModelMapper walletModelMapper;

    @BeforeEach
    void setup() {
        instructorService = new InstructorServiceImpl(instructorRepository, walletModelMapper);
    }

    @Test
    void shouldCreateInstructor() {
        // given
        Instructor instructorSaved = createInstructor();
        when(instructorRepository.save(any())).thenReturn(instructorSaved);
        when(instructorRepository.existsById(any())).thenReturn(true);
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME, LAST_NAME, EMAIL, BIO);

        // when
        InstructorDTO instructorDTO = instructorService.createInstructor(inputInstructorDTO);

        // then
        assertThat(instructorDTO.id()).isEqualTo(instructorSaved.getId());
        assertThat(instructorDTO.firstName()).isEqualTo(instructorSaved.getFirstName());
        assertThat(instructorDTO.lastName()).isEqualTo(instructorSaved.getLastName());
        assertThat(instructorDTO.email()).isEqualTo(instructorSaved.getEmail());
        assertThat(instructorDTO.bio()).isEqualTo(instructorSaved.getBio());
    }

    @Test
    void shouldThrowEntityNotSavedException() {
        // given
        Instructor instructorSaved = createInstructor();
        when(instructorRepository.save(any())).thenReturn(instructorSaved);
        when(instructorRepository.existsById(any())).thenReturn(false);
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME, LAST_NAME, EMAIL, BIO);

        // when

        // then
        assertThatThrownBy(() -> instructorService.createInstructor(inputInstructorDTO))
                .isInstanceOf(EntityNotSavedException.class)
                .hasMessageStartingWith("An error occurred. Instructor can not be saved.");
    }

    private Instructor createInstructor() {
        Instructor instructor = new Instructor();
        instructor.setId(ID);
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        instructor.setEmail(EMAIL);
        instructor.setBio(BIO);
        return instructor;
    }
}
