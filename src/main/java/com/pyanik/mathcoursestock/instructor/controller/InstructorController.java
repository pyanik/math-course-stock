package com.pyanik.mathcoursestock.instructor.controller;

import com.pyanik.mathcoursestock.instructor.persistence.InputInstructorDTO;
import com.pyanik.mathcoursestock.instructor.persistence.InstructorDTO;
import com.pyanik.mathcoursestock.instructor.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("")
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InputInstructorDTO inputInstructorDTO) {
        InstructorDTO savedInstructor = instructorService.createInstructor(inputInstructorDTO);
        return ResponseEntity.ok(savedInstructor);
    }

    @GetMapping("")
    public ResponseEntity<List<InstructorDTO>> getInstructors() {
        List<InstructorDTO> allInstructors = instructorService.getAllInstructors();

        if (!allInstructors.isEmpty()) {
            return ResponseEntity.ok(allInstructors);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<InstructorDTO> getInstructor(@PathVariable Long id) {
        InstructorDTO Instructor = instructorService.getInstructor(id);

        return ResponseEntity.ok(Instructor);
    }

    @PutMapping("{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable Long instructorId, @Valid @RequestBody InputInstructorDTO inputInstructorDTO) {
        InstructorDTO updatedInstructor = instructorService.replaceInstructor(instructorId, inputInstructorDTO);
        return ResponseEntity.ok(updatedInstructor);
    }
}