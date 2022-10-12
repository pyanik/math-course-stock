package com.pyanik.mathcoursestock.instructor.persistence;

import javax.validation.constraints.*;

public record InputInstructorDTO(@NotBlank(message = "first name must be present")
                                 @NotEmpty(message = "first name must not be empty")
                                 @Size(max = 20, message = "first name must not be longer than 20 letters")
                                 @Pattern(regexp = "[a-z A-Z]+", message = "first name must contains only letters") String firstName,

                                 @NotBlank(message = "last name must be present")
                                 @NotEmpty(message = "last name must not be empty")
                                 @Size(max = 20, message = "last name must not be longer than 20 letters")
                                 @Pattern(regexp = "[a-z A-Z]+", message = "last name must contains only letters") String lastName,

                                 @Email(message = "email should be a valid email") String email,

                                 @Size(max = 100, message = "bio must not be longer than 100 characters") String bio) {
}
