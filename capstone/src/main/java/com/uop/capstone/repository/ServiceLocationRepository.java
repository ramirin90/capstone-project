package com.uop.capstone.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uop.capstone.model.ServiceLocation;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, Long> {
	
	Optional<ServiceLocation> findByName(String name);
	
//	ServiceLocationRepository findById(String name);
}
	