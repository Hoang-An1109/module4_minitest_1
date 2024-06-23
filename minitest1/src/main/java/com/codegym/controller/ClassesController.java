package com.codegym.controller;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import com.codegym.model.dto.IClassesCount;
import com.codegym.service.IClassesService;
import com.codegym.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/classes")
public class ClassesController {
    @Autowired
    private IClassesService classesService;

    @Autowired
    private IStudentService studentService;

    @GetMapping
    public ModelAndView listClasses(){
        ModelAndView modelAndView = new ModelAndView("/classes/list");
        Iterable<Classes> classes = classesService.findAll();
        modelAndView.addObject("classes", classes);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/classes/create");
        modelAndView.addObject("classes", new Classes());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("classes") Classes classes,
                         RedirectAttributes redirectAttributes) {
        classesService.save(classes);
        redirectAttributes.addFlashAttribute("message", "Create new classes successfully");
        return "redirect:/classes";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Classes> classes = classesService.findById(id);
        if (classes.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/classes/update");
            modelAndView.addObject("classes", classes.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("classes") Classes classes,
                         RedirectAttributes redirect) {
        classesService.save(classes);
        redirect.addFlashAttribute("message", "Update classes successfully");
        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        classesService.remove(id);
        redirect.addFlashAttribute("message", "Delete classes successfully");
        return "redirect:/classes";
    }

    @GetMapping("/view-classes/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Optional<Classes> classesOptional = classesService.findById(id);
        if(!classesOptional.isPresent()){
            return new ModelAndView("/error");
        }

        Iterable<Student> students = studentService.findAllByClasses(classesOptional.get());

        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/student-counts")
    public ModelAndView listStudentCounts() {
        List<IClassesCount> classesCounts = classesService.getClassesCounts();
        ModelAndView modelAndView = new ModelAndView("/classes/student-counts");
        modelAndView.addObject("classesCounts", classesCounts);
        return modelAndView;
    }
}
