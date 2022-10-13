package com.pyanik.mathcoursestock.instructor.service;

import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;

import java.util.List;

public interface InstructorService {
    InstructorDTO createInstructor(InputInstructorDTO inputInstructorDTO);

    List<InstructorDTO> getAllInstructors();

    InstructorDTO getInstructor(Long id);
}