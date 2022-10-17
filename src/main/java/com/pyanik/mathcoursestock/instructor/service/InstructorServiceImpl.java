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
        Instructor instructor = instructorModelMapper.mapInputInstructorDTOToInstructorEntity(inputInstructorDTO);
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
            throw new DataNotFoundException("An error occurred while retrieving the list of instructors.");
        }
    }

    @Override
    public InstructorDTO getInstructor(Long instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (instructorOptional.isPresent())
            return instructorModelMapper.mapInstructorEntityToInstructorDTO(instructorOptional.get());
        else
            throw new DataNotFoundException(String.format("Instructor with id %s could not be found.", instructorId));


    }

    @Override
    public InstructorDTO replaceInstructor(Long instructorId, InputInstructorDTO inputInstructorDTO) {
        if (!instructorRepository.existsById(instructorId)) {
            throw new DataNotFoundException(String.format("Instructor with id %s could not be found.", instructorId));
        }
        Instructor instructorToUpdate = instructorModelMapper.mapInputInstructorDTOToInstructorEntity(inputInstructorDTO);
        instructorToUpdate.setId(instructorId);
        Instructor updatedInstructor = instructorRepository.save(instructorToUpdate);
        return instructorModelMapper.mapInstructorEntityToInstructorDTO(updatedInstructor);
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        if (!instructorRepository.existsById(instructorId)) {
            throw new DataNotFoundException(String.format("Instructor with id %s could not be found.", instructorId));
        }
        instructorRepository.deleteById(instructorId);
    }
}
