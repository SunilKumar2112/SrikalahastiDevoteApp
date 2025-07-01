package com.temples.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.temples.dto.DevoteeDto;
import com.temples.model.DevoteeEntity;
import com.temples.repo.DevoteeRepo;
@Service
public class DevoteeServiceImpl  implements DevoteeService{
	@Autowired
	DevoteeRepo devoteeRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
    public DevoteeDto createDevotee(DevoteeDto devotee) {
        // Log input for debugging
        System.out.println("Input DevoteeDto: " + devotee);

        // Check if phoneNumber already exists
        if (devotee.getPhoneNumber() != null && devoteeRepo.findByPhoneNumber(devotee.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number " + devotee.getPhoneNumber() + " is already registered.");
        }

        // Manually map DevoteeDto to DevoteeEntity
        DevoteeEntity devoteeEntity = new DevoteeEntity();
        devoteeEntity.setName(devotee.getName());
        devoteeEntity.setAge(devotee.getAge());
        devoteeEntity.setPhoneNumber(devotee.getPhoneNumber());
        devoteeEntity.setDate(devotee.getDate());
        devoteeEntity.setGender(devotee.getGender());
        System.out.println("Mapped DevoteeEntity: " + devoteeEntity);

        // Save the entity to the repository
        DevoteeEntity savedEntity = devoteeRepo.save(devoteeEntity);

        // Manually map saved DevoteeEntity back to DevoteeDto
        DevoteeDto result = new DevoteeDto();
        result.setName(savedEntity.getName());
        result.setAge(savedEntity.getAge());
        result.setPhoneNumber(savedEntity.getPhoneNumber());
        result.setDate(savedEntity.getDate());
        result.setGender(savedEntity.getGender());
        System.out.println("Output DevoteeDto: " + result);

        return result;
    }

}
