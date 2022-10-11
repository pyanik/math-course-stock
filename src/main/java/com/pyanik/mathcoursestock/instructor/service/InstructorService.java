package com.pyanik.mathcoursestock.instructor.service;

import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;

public interface InstructorService {
    InstructorDTO createInstructor(InputInstructorDTO inputInstructorDTO);
}