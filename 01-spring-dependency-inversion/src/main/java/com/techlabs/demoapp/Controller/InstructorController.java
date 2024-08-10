package com.techlabs.demoapp.Controller;

import com.techlabs.demoapp.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstructorController {


    private Instructor instructor1;
    private Instructor instructor2;


    @Autowired
    @Qualifier(value = "pythonInstructor")
    public void setInstructor1(Instructor instructor1){
        this.instructor1=instructor1;
    }

    @Value("${myapp.name}")
    private String name;


    @Autowired
    @Qualifier(value = "javaInstructor")
    public void setInstructor2(Instructor instructor2){
        this.instructor2=instructor2;
    }

    @GetMapping("/train")
    public String getMessage(){
        return "Is bean singleton : "+this.instructor1.equals(instructor2)+" " +instructor1.getClass().getName()+
                " "+instructor2.getClass().getName()+" "+name;
    }

}