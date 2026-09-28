package com.uop.capstone.model;

import java.util.List;

import com.uop.capstone.dto.ServiceType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
public class ServiceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



	private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ServiceType type; // MEDICAL, FOOD_BANK, SHELTER, EMERGENCY, etc.

    // Location details
    private String address;
    private Double latitude;
    private Double longitude;

    // Contact info
    private String phone;
    private String email;
    private String website;

    // Capacity (optional)
    private Integer capacity;

    @OneToMany(mappedBy = "serviceLocation", cascade = CascadeType.ALL)
    private List<OperatingHour> operatingHours;

    @OneToMany(mappedBy = "serviceLocation", cascade = CascadeType.ALL)
    private List<ResourceTag> tags;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public List<OperatingHour> getOperatingHours() {
		return operatingHours;
	}

	public void setOperatingHours(List<OperatingHour> operatingHours) {
		this.operatingHours = operatingHours;
	}

	public List<ResourceTag> getTags() {
		return tags;
	}

	public void setTags(List<ResourceTag> tags) {
		this.tags = tags;
	}
}
