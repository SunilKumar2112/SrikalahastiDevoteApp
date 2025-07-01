package com.temples;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.temples.dto.DevoteeDto;
import com.temples.model.DevoteeEntity;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class SriKalahasthriDevoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SriKalahasthriDevoteAppApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	    modelMapper.typeMap(DevoteeDto.class, DevoteeEntity.class)
	        .addMappings(mapper -> {
	            mapper.map(DevoteeDto::getName, DevoteeEntity::setName);
	            mapper.map(DevoteeDto::getAge, DevoteeEntity::setAge);
	            mapper.map(DevoteeDto::getPhoneNumber, DevoteeEntity::setPhoneNumber);
	            mapper.map(DevoteeDto::getDate, DevoteeEntity::setDate);
	            mapper.map(DevoteeDto::getGender, DevoteeEntity::setGender);
	        });
	    modelMapper.typeMap(DevoteeEntity.class, DevoteeDto.class)
	        .addMappings(mapper -> {
	            mapper.map(DevoteeEntity::getName, DevoteeDto::setName);
	            mapper.map(DevoteeEntity::getAge, DevoteeDto::setAge);
	            mapper.map(DevoteeEntity::getPhoneNumber, DevoteeDto::setPhoneNumber);
	            mapper.map(DevoteeEntity::getDate, DevoteeDto::setDate);
	            mapper.map(DevoteeEntity::getGender, DevoteeDto::setGender);
	        });
	    return modelMapper;
	}
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("bearer-jwt",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("jwt")
								.in(SecurityScheme.In.HEADER).name("Authorization")))
				.info(new Info().title("App API").version("snapshot"))
				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
	}

	
}
