package com.pyanik.mathcoursestock.instructor.service;

import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstructorModelMapper {
    InstructorDTO mapInstructorEntityToInstructorDTO(Instructor instructor);

    @Mapping(target = "id", ignore = true)
    Instructor mapCreateInstructorDTOToInstructorEntity(InputInstructorDTO inputInstructorDTO);
}
