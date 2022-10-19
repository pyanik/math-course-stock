package com.pyanik.mathcoursestock.course.persistence;

import com.pyanik.mathcoursestock.instructor.persistence.Instructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Embedded
    private Price price;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
