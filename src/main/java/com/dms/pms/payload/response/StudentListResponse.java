package com.dms.pms.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class StudentListResponse {
    @ApiModelProperty(example = "<유저 이름>", value = "유저 이름")
    @JsonProperty("name")
    private String name;

    @JsonProperty("students")
    private List<Student> students = new ArrayList<>();

    public void setName(String name) { this.name = name; }

    public void addStudent(Student student) {
        students.add(student);
    }

    public static class Student {
        public Student(Integer number, String name) {
            this.number = number;
            this.name = name;
        }

        @ApiModelProperty(example = "<학생 학번>", value = "학생 학번")
        @JsonProperty("student-number")
        private Integer number;

        @ApiModelProperty(example = "<학생 이름>", value = "학생 이름")
        @JsonProperty("student-name")
        private String name;
    }
}
