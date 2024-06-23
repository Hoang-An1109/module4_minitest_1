package com.codegym.service;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService extends IGenerateService<Student> {
    Iterable<Student> findAllByClasses(Classes classes);

    Page<Student> findAll(Pageable pageable);

    Page<Student> findAllByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
}
