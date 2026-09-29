package com.uop.capstone.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uop.capstone.dto.ServiceType;
import com.uop.capstone.model.ServiceLocation;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, Long> {
	
	Optional<ServiceLocation> findByName(String name);
	
	Optional<List<ServiceLocation>> findByType(ServiceType type);
	
//	ServiceLocationRepository findById(String name);
}
	