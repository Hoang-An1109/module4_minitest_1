package com.codegym.service;

import com.codegym.model.Classes;
import com.codegym.model.dto.IClassesCount;

import java.util.List;

public interface IClassesService extends IGenerateService<Classes> {
    List<IClassesCount> getClassesCounts();
}
