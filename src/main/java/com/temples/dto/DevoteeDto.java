package com.temples.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class DevoteeDto {
   
    private String name;

    private Integer age;

    private String phoneNumber;

    private LocalDate date;

    private String gender;

	public DevoteeDto(String name, Integer age, String phoneNumber, LocalDate date, String gender) {
		super();
		
		this.name = name;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.gender = gender;
	}

	public DevoteeDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
    

}
