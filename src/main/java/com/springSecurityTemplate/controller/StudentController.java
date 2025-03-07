package com.springSecurityTemplate.controller;

import com.springSecurityTemplate.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Suman", "Java"),
            new Student(2, "Saurav","Python")
    ));


    @GetMapping("/students")
   // @PreAuthorize("hasRole('USER')")
    public List<Student> getStudents(){
        return students;
    }

//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest httpServletRequest){
//        //return (CsrfToken)httpServletRequest.getAttribute("_csrf");
//        CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute("_csrf");
//        System.out.println(csrfToken.getToken());
//        return csrfToken;
//    }


    @PostMapping("/students")
    //@PreAuthorize("hasRole('ADMIN')")
    public Student addStudent(@RequestBody Student student){
        students.add(student);
        return student;
    }

}