package com.pyanik.mathcoursestock.instructor.controller;

import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;
import com.pyanik.mathcoursestock.instructor.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/instructors")
@RequiredArgsConstructor
class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("")
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InputInstructorDTO inputInstructorDTO) {
        InstructorDTO savedInstructor = instructorService.createInstructor(inputInstructorDTO);
        return ResponseEntity.ok(savedInstructor);
    }
}