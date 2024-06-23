package com.codegym.service.impl;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import com.codegym.repository.IClassesRepository;
import com.codegym.repository.IStudentRepository;
import com.codegym.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements IStudentService {

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IClassesRepository classesRepository;

    @Override
    public Iterable<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Iterable<Student> findAllByClasses(Classes classes) {
        return studentRepository.findAllByClasses(classes);
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> findAllByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable) {
        return studentRepository.findAllByFirstNameContainingOrLastNameContaining(firstName, lastName, pageable);
    }
}
