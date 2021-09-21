package ru.saprcorset.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}

