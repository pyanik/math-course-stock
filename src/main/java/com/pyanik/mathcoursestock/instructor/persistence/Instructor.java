package com.pyanik.mathcoursestock.instructor.persistence;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "instructors")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String bio;
}
