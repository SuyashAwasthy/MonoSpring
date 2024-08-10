package com.techlabs.demoapp.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PythonInstructor implements Instructor {
    public PythonInstructor() {
        System.out.println("Python");
    }

    @Override
    public String getTrainingPlan() {
        return "Practice Python";
    }


}