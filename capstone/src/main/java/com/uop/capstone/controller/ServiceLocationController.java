package com.uop.capstone.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uop.capstone.dto.ServiceLocationRequest;
import com.uop.capstone.model.ServiceLocation;
import com.uop.capstone.service.ServiceLocationService;

@RestController
@RequestMapping("/api/locations")
public class ServiceLocationController {

	private final ServiceLocationService service;

	public ServiceLocationController(ServiceLocationService service) {
		this.service = service;
	}

	@PostMapping
	public ServiceLocation createLocation(@RequestBody ServiceLocationRequest request) {
		return service.addLocation(request);
	}
}
