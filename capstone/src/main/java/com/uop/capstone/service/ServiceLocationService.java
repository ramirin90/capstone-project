package com.uop.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import com.uop.capstone.dto.ServiceLocationRequest;
import com.uop.capstone.dto.ServiceType;
import com.uop.capstone.model.OperatingHour;
import com.uop.capstone.model.ResourceTag;
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
		
		List<ResourceTag> tags = request.tags().stream()
			    .map(t -> {
			        ResourceTag tag = new ResourceTag();
			        tag.setLabel(t);                 // set label
			        tag.setServiceLocation(location); // set parent
			        return tag;
			    })
			    .toList();

			// IMPORTANT: attach tags to the location
			location.setTags(tags);

			return repository.save(location);
	}
	
	public List<ServiceLocation> getAllLocations() {
	    return repository.findAll();
	}
	
	public ResponseEntity<ServiceLocation> getLocationById(long id) {
		 return repository.findById(id).
				 map(ResponseEntity::ok)
				 .orElse(ResponseEntity.notFound().build());
	}
	
	public ResponseEntity<ServiceLocation> getLocationByName(String name) {
		return repository.findByName(name).
				map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	public ResponseEntity<?> getLocationByType(String type) {
		
//		return repository.findByType(ServiceType.valueOf(name)).
//				map(ResponseEntity::ok)
//				.orElse(ResponseEntity.notFound().build());
		
	    ServiceType enumType;

	    try {
	        enumType = ServiceType.valueOf(type.toUpperCase());
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Invalid type: " + type);
	    }

	    Optional<List<ServiceLocation>> result = repository.findByType(enumType);

	    if (result.isEmpty() || result.get().isEmpty()) {
	        return ResponseEntity.status(404).body("No locations found for type: " + type);
	    }

	    return ResponseEntity.ok(result.get());
		
	}
	
	
}
