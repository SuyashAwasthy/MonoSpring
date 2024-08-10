package com.techlabs.demoapp.config;

import com.techlabs.demoapp.model.Instructor;
import com.techlabs.demoapp.model.JavaInstructor;
import com.techlabs.demoapp.model.PythonInstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class InstructorConfig {

    @Bean(name = "javaInstructor")
    public Instructor getJavaInstructor(){
        return new JavaInstructor();
    }

    @Bean(name = "pythonInstructor")
    @Primary
    public Instructor getPythonInstructor(){
        return new PythonInstructor();
    }

}