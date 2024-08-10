package com.techlabs.demoapp.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class JavaInstructor implements Instructor {
    public JavaInstructor() {
        System.out.println("Java");
    }

    @Override
    public String getTrainingPlan() {
        return "Practice Java";
    }
}
