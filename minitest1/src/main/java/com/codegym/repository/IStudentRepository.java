package com.codegym.repository;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student, Long> {
    Iterable<Student> findAllByClasses(Classes classes);

    Page<Student> findAll(Pageable pageable);

    Page<Student> findAllByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
}
