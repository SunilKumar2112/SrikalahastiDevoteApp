package com.temples.service;



import org.springframework.stereotype.Service;

import com.temples.dto.DevoteeDto;
@Service
public interface DevoteeService {
	public DevoteeDto createDevotee(DevoteeDto devotee);
}
