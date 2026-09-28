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

@Entity
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
}
