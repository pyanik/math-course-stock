package com.pyanik.mathcoursestock.Instructor.service;

import com.pyanik.mathcoursestock.exception.EntityNotSavedException;
import com.pyanik.mathcoursestock.exception.DataNotFoundException;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InstructorServiceImplTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final String FIRST_NAME_1 = "John";
    private static final String FIRST_NAME_2 = "Keith";
    private static final String FIRST_NAME_3 = "Nick";
    private static final String LAST_NAME_1 = "James";
    private static final String LAST_NAME_2 = "Jarrett";
    private static final String LAST_NAME_3 = "Cave";
    private static final String EMAIL_1 = "john.james@mail.com";
    private static final String EMAIL_2 = "keith.jarrett@mail.com";
    private static final String EMAIL_3 = "nick.cave@mail.com";
    private static final String BIO_1 = "Sample bio 1";
    private static final String BIO_2 = "Sample bio 2";
    private static final String BIO_3 = "Sample bio 3";

    private InstructorService instructorService;

    @Mock
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorModelMapper instructorModelMapper;

    @BeforeEach
    void setup() {
        instructorService = new InstructorServiceImpl(instructorRepository, instructorModelMapper);
    }

    @Test
    void shouldCreateInstructor() {
        // given
        Instructor savedInstructor = createInstructor();
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME_1, LAST_NAME_1, EMAIL_1, BIO_1);

        // when
        when(instructorRepository.save(any())).thenReturn(savedInstructor);
        when(instructorRepository.existsById(any())).thenReturn(true);
        InstructorDTO instructorDTO = instructorService.createInstructor(inputInstructorDTO);

        // then
        assertThat(instructorDTO.id()).isEqualTo(savedInstructor.getId());
        assertThat(instructorDTO.firstName()).isEqualTo(savedInstructor.getFirstName());
        assertThat(instructorDTO.lastName()).isEqualTo(savedInstructor.getLastName());
        assertThat(instructorDTO.email()).isEqualTo(savedInstructor.getEmail());
        assertThat(instructorDTO.bio()).isEqualTo(savedInstructor.getBio());
    }

    @Test
    void shouldThrowExceptionForNotSavedInstructor() {
        // given
        Instructor savedInstructor = createInstructor();
        InputInstructorDTO inputInstructorDTO = new InputInstructorDTO(FIRST_NAME_1, LAST_NAME_1, EMAIL_1, BIO_1);

        // when
        when(instructorRepository.save(any())).thenReturn(savedInstructor);
        when(instructorRepository.existsById(any())).thenReturn(false);

        // then
        assertThatThrownBy(() -> instructorService.createInstructor(inputInstructorDTO))
                .isInstanceOf(EntityNotSavedException.class)
                .hasMessageStartingWith("An error occurred. Instructor can not be saved.");
    }

    @Test
    void shouldReturnListOfInstructorDTO() {
        // given
        List<Instructor> instructorsList = createListOfInstructors();

        // when
        Mockito.when(instructorRepository.findAll()).thenReturn(instructorsList);
        List<InstructorDTO> allInstructors = instructorService.getAllInstructors();

        // then
        assertThat(allInstructors).hasSize(instructorsList.size());
        assertThat(allInstructors.get(0).firstName()).isEqualTo(FIRST_NAME_1);
        assertThat(allInstructors.get(0).lastName()).isEqualTo(LAST_NAME_1);
        assertThat(allInstructors.get(0).email()).isEqualTo(EMAIL_1);
        assertThat(allInstructors.get(0).bio()).isEqualTo(BIO_1);
        assertThat(allInstructors.get(1).firstName()).isEqualTo(FIRST_NAME_2);
        assertThat(allInstructors.get(1).lastName()).isEqualTo(LAST_NAME_2);
        assertThat(allInstructors.get(1).email()).isEqualTo(EMAIL_2);
        assertThat(allInstructors.get(1).bio()).isEqualTo(BIO_2);
        assertThat(allInstructors.get(2).firstName()).isEqualTo(FIRST_NAME_3);
        assertThat(allInstructors.get(2).lastName()).isEqualTo(LAST_NAME_3);
        assertThat(allInstructors.get(2).email()).isEqualTo(EMAIL_3);
        assertThat(allInstructors.get(2).bio()).isEqualTo(BIO_3);
    }

    @Test
    void shouldThrowExceptionWhenListOfInstructorsNotFound() {
        // given
        Mockito.when(instructorRepository.findAll()).thenThrow(RuntimeException.class);

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> instructorService.getAllInstructors());

        // then
        assertThat(exception)
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("An error occurred while retrieving the list of instructors.");
    }

    @Test
    void shouldReturnInstructorWithGivenId() {
        // given
        Instructor instructor = createInstructor();

        // when
        Mockito.when(instructorRepository.findById(ID_1)).thenReturn(Optional.of(instructor));
        InstructorDTO instructorRetrieved = instructorService.getInstructor(ID_1);

        // then
        assertThat(instructorRetrieved.id()).isEqualTo(ID_1);
        assertThat(instructorRetrieved.firstName()).isEqualTo(FIRST_NAME_1);
        assertThat(instructorRetrieved.lastName()).isEqualTo(LAST_NAME_1);
        assertThat(instructorRetrieved.email()).isEqualTo(EMAIL_1);
        assertThat(instructorRetrieved.bio()).isEqualTo(BIO_1);
    }

    @Test
    void shouldThrowExceptionWhenInstructorNotFound() {
        // given
        Mockito.when(instructorRepository.findById(ID_1)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(RuntimeException.class, () -> instructorService.getInstructor(ID_1));

        // then
        assertThat(exception)
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(String.format("Instructor with id %s could not be found.", ID_1));
    }

    @Test
    void shouldUpdateInstructor() {
        // given
        InputInstructorDTO inputInstructorDTO = createInputInstructorDTO();
        Instructor updatedInstructor = createUpdatedInstructor();

        // when
        when(instructorRepository.existsById(ID_1)).thenReturn(true);
        when(instructorRepository.save(any())).thenReturn(updatedInstructor);
        InstructorDTO instructorDTO = instructorService.replaceInstructor(ID_1, inputInstructorDTO);

        // then
        assertThat(instructorDTO.id()).isEqualTo(updatedInstructor.getId());
        assertThat(instructorDTO.firstName()).isEqualTo(updatedInstructor.getFirstName());
        assertThat(instructorDTO.lastName()).isEqualTo(updatedInstructor.getLastName());
        assertThat(instructorDTO.email()).isEqualTo(updatedInstructor.getEmail());
        assertThat(instructorDTO.bio()).isEqualTo(updatedInstructor.getBio());
    }

    @Test
    void shouldThrowExceptionWhenInstructorToUpdateNotFound() {
        // given
        InputInstructorDTO inputInstructorDTO = createInputInstructorDTO();

        // when
        Mockito.when(instructorRepository.existsById(ID_1)).thenReturn(false);
        Exception exception = assertThrows(RuntimeException.class, () -> instructorService.replaceInstructor(ID_1, inputInstructorDTO));

        // then
        assertThat(exception)
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(String.format("Instructor with id %s could not be found.", ID_1));
    }

    private Instructor createUpdatedInstructor() {
        return new Instructor(ID_1, FIRST_NAME_2, LAST_NAME_2, EMAIL_2, BIO_3);
    }

    private InputInstructorDTO createInputInstructorDTO() {
        return new InputInstructorDTO(FIRST_NAME_2, LAST_NAME_2, EMAIL_2, BIO_3);
    }

    private Instructor createInstructor() {
        return new Instructor(ID_1, FIRST_NAME_1, LAST_NAME_1, EMAIL_1, BIO_1);
    }

    private List<Instructor> createListOfInstructors() {
        Instructor instructor1 = new Instructor(ID_1, FIRST_NAME_1, LAST_NAME_1, EMAIL_1, BIO_1);
        Instructor instructor2 = new Instructor(ID_2, FIRST_NAME_2, LAST_NAME_2, EMAIL_2, BIO_2);
        Instructor instructor3 = new Instructor(ID_3, FIRST_NAME_3, LAST_NAME_3, EMAIL_3, BIO_3);
        return List.of(instructor1, instructor2, instructor3);
    }
}
