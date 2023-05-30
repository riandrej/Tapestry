package com.example.tapestry.employees;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Property
    private Integer id;
    @Property
    private String name;
    @Property
    private String lastName;
    @Property
    @Persist
    private Title title;
    @Property
    @Persist
    private Gender gender;
    @Property
    private LocalDate dateOfBirth;
    @Property
    private String photoFilePath;

}
