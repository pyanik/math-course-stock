package com.pyanik.mathcoursestock.instructor.service;

import com.pyanik.mathcoursestock.exception.EntityNotSavedException;
import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.Instructor;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    private final InstructorModelMapper instructorModelMapper;

    @Override
    public InstructorDTO createInstructor(InputInstructorDTO inputInstructorDTO) {
        Instructor instructor = instructorModelMapper.mapCreateInstructorDTOToInstructorEntity(inputInstructorDTO);
        Instructor savedInstructor = instructorRepository.save(instructor);
        boolean isInstructorExists = instructorRepository.existsById(savedInstructor.getId());

        if (isInstructorExists) {
            return instructorModelMapper.mapInstructorEntityToInstructorDTO(savedInstructor);
        }
        throw new EntityNotSavedException("An error occurred. Instructor can not be saved.");
    }
}
