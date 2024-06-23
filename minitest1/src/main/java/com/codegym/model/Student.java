package com.codegym.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "students")
@Component
public class Student implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int age;
    private String img;
    private String phoneNumber;
    private Date dob;
    private String address;
    private Double mark;

    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    public Student() {
    }

    public Student(String firstName, String lastName, int age, String img, String phoneNumber, Date dob, String address, Double mark, Classes classes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.img = img;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.address = address;
        this.mark = mark;
        this.classes = classes;
    }

    public Student(Long id, String firstName, String lastName, int age, String img, String phoneNumber, Date dob, String address, Double mark, Classes classes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.img = img;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.address = address;
        this.mark = mark;
        this.classes = classes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;

        //FIRST NAME
        String firstName = student.getFirstName();
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        if (firstName.length() > 50) {
            errors.rejectValue("firstName", "firstName.length");
        }
        if (!firstName.matches("^[a-zA-Z]*$")) {
            errors.rejectValue("firstName", "firstName.matches");
        }

        //LAST NAME
        String lastName = student.getLastName();
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");
        if (lastName.length() > 50) {
            errors.rejectValue("lastName", "lastName.length");
        }
        if (!lastName.matches("^[a-zA-Z]*$")) {
            errors.rejectValue("lastName", "lastName.matches");
        }

        //AGE
        int age = student.getAge();
        ValidationUtils.rejectIfEmpty(errors, "age","age.empty");
        if (age < 0 || age > 100) {
            errors.rejectValue("age", "age.range");
        }

        //IMG
        String img = student.getImg();
        ValidationUtils.rejectIfEmpty(errors, "img", "img.empty");

        //PHONE NUMBER
        String phoneNumber = student.getPhoneNumber();
        ValidationUtils.rejectIfEmpty(errors,"phoneNumber", "phoneNumber.empty");
        if (phoneNumber.length()>11 || phoneNumber.length()<10){
            errors.rejectValue("phoneNumber", "phoneNumber.length");
        }
        if (!phoneNumber.startsWith("0")){
            errors.rejectValue("phoneNumber", "phoneNumber.startsWith");
        }
        if (!phoneNumber.matches("(^$|[0-9]*$)")){
            errors.rejectValue("phoneNumber", "phoneNumber.matches");
        }

        //DOB
        Date dob = student.getDob();
        ValidationUtils.rejectIfEmpty(errors, "dob", "dob.empty");
        if (dob != null) {
            LocalDate dobLocalDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (dobLocalDate.isAfter(LocalDate.now())) {
                errors.rejectValue("dob", "dob.future");
            }
        }

        //ADDRESS
        String address = student.getAddress();
        ValidationUtils.rejectIfEmpty(errors, "address", "address.empty");
        if (address.length() > 100) {
            errors.rejectValue("address", "address.length");
        }

        //MARK
        Double mark = student.getMark();
        ValidationUtils.rejectIfEmpty(errors, "mark", "mark.empty");
        if (mark != null && (mark < 0 || mark > 10)) {
            errors.rejectValue("mark", "mark.range");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }
}
