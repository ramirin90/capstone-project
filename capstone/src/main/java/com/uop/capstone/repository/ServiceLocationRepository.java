package com.uop.capstone.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.uop.capstone.model.ServiceLocation;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, Long> {
}
	