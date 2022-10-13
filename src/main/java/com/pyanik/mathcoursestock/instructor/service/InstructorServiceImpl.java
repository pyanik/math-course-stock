package com.pyanik.mathcoursestock.instructor.service;

import com.pyanik.mathcoursestock.exception.EntityNotSavedException;
import com.pyanik.mathcoursestock.exception.DataNotFoundException;
import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.Instructor;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    @Override
    public List<InstructorDTO> getAllInstructors() {
        try {
            log.debug("Return list of all instructors");
            return instructorRepository.findAll()
                    .stream()
                    .map(instructorModelMapper::mapInstructorEntityToInstructorDTO)
                    .toList();
        } catch (RuntimeException e ) {
            log.error("List of instructors not found");
            throw new DataNotFoundException("An error occurred while retrieving the list of instructors");
        }
    }

    @Override
    public InstructorDTO getInstructor(Long id) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);
        if (instructorOptional.isPresent())
            return instructorModelMapper.mapInstructorEntityToInstructorDTO(instructorOptional.get());
        else
            throw new DataNotFoundException("Instructor could not be found " + id);


    }
}
