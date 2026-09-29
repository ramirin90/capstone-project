package com.uop.capstone.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uop.capstone.dto.ServiceLocationRequest;
import com.uop.capstone.model.ServiceLocation;
import com.uop.capstone.service.ServiceLocationService;

@RestController
@RequestMapping("/capstone")
public class ServiceLocationController {

	private final ServiceLocationService service;

	public ServiceLocationController(ServiceLocationService service) {
		this.service = service;
	}

	@PostMapping("/locations")
	public ServiceLocation createLocation(@RequestBody ServiceLocationRequest request) {
		return service.addLocation(request);
	}
	
	@GetMapping("/locations")
	public List<ServiceLocation> getAllLocations() {
	    return service.getAllLocations();
	}
	
	@GetMapping("/locations/id/{id}")
	public ResponseEntity<ServiceLocation> getAllLocations(@PathVariable int id) {
	    return service.getLocationById(id);
	}
	
	@GetMapping("/locations/name/{name}")
	public ResponseEntity<ServiceLocation> getAllLocationByName(@PathVariable String name) {
	    return service.getLocationByName(name);
	}
	
}
