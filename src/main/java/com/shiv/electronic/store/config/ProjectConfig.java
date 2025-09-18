package com.shiv.electronic.store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    //ModelMapper for DTO mapping
    @Bean
    public ModelMapper generateModelMapper(){
        return new ModelMapper();
    }
}
