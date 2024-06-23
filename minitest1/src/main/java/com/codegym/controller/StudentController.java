package com.codegym.controller;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import com.codegym.service.IClassesService;
import com.codegym.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private IClassesService classesService;

    @ModelAttribute("classes")
    public Iterable<Classes> listClasses() {
        return classesService.findAll();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping
    public ModelAndView listStudent(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/student/list");
        Page<Student> students = studentService.findAll(pageable);
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView listStudentsSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Student> students;
        if(search.isPresent()){
            String searchTerm = search.get().trim();
            students = studentService.findAllByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm, pageable);
        } else {
            students = studentService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/student/create");
        modelAndView.addObject("student", new Student());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("student") Student student,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        new Student().validate(student, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/student/create";
        }
        studentService.save(student);
        redirectAttributes.addFlashAttribute("message", "Create new student successfully");
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (student.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/student/update");
            modelAndView.addObject("student", student.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("student") Student student,
                         RedirectAttributes redirect) {
        studentService.save(student);
        redirect.addFlashAttribute("message", "Update student successfully");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        studentService.remove(id);
        redirect.addFlashAttribute("message", "Delete student successfully");
        return "redirect:/students";
    }
}
