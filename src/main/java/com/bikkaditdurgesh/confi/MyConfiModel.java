package com.bikkaditdurgesh.confi;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MyConfiModel {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper() ;
		
	}

}
