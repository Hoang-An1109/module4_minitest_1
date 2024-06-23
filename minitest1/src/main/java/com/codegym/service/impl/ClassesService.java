package com.codegym.service.impl;

import com.codegym.model.Classes;
import com.codegym.model.dto.IClassesCount;
import com.codegym.repository.IClassesRepository;
import com.codegym.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassesService implements IClassesService {

    @Autowired
    private IClassesRepository classesRepository;

    @Override
    public Iterable<Classes> findAll() {
        return classesRepository.findAll();
    }

    @Override
    public void save(Classes classes) {
        classesRepository.save(classes);
    }

    @Override
    public Optional<Classes> findById(Long id) {
        return classesRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        classesRepository.deleteById(id);
    }

    @Override
    public List<IClassesCount> getClassesCounts() {
        return classesRepository.getClassesCounts();
    }
}
