package com.uop.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uop.capstone.dto.ServiceLocationRequest;
import com.uop.capstone.model.OperatingHour;
import com.uop.capstone.model.ServiceLocation;
import com.uop.capstone.repository.ServiceLocationRepository;

@Service
public class ServiceLocationService {

	private final ServiceLocationRepository repository;

	public ServiceLocationService(ServiceLocationRepository repository) {
		this.repository = repository;
	}

	public ServiceLocation addLocation(ServiceLocationRequest request) {

		ServiceLocation location = new ServiceLocation();
		location.setName(request.name());
		location.setDescription(request.description());
		location.setType(request.type());
		location.setAddress(request.address());
		location.setLatitude(request.latitude());
		location.setLongitude(request.longitude());
		location.setPhone(request.phone());
		location.setEmail(request.email());
		location.setWebsite(request.website());

		var hours = request.operatingHours().stream().map(h -> {
			OperatingHour oh = new OperatingHour();
			oh.setDays(h.days());
			oh.setOpenTime(h.openTime());
			oh.setCloseTime(h.closeTime());
			oh.setServiceLocation(location);
			return oh;
		}).toList();

		location.setOperatingHours(hours);

		return repository.save(location);
	}
	
	public List<ServiceLocation> getAllLocations() {
	    return repository.findAll();
	}
}
