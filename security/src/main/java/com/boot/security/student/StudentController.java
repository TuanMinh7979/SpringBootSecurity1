package com.boot.security.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
        private static final List<Student> STUDENTS = Arrays.asList(
                        new Student(1, "tuansuper"),
                        new Student(2, "tuan heroupdate"),
                        new Student(3, "any hot girl")

        );

        @GetMapping("/{studentId}")
        public Student getStudent(@PathVariable("studentId") Integer studentid) {
                System.out.println("vao day roi");
                return STUDENTS.stream()
                                .filter(st -> studentid.equals(st.getStudentId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException(">>>student " + studentid
                                                + " is not exist"));
        }
}
