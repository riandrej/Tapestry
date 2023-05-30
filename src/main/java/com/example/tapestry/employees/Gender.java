package com.example.tapestry.employees;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    HYBRID("HYBRID");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
