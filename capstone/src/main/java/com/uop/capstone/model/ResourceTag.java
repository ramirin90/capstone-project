package com.uop.capstone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ResourceTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label; // e.g., "24/7", "Free Meals", "Emergency Hotline"

    @ManyToOne
    @JoinColumn(name = "service_location_id")
    private ServiceLocation serviceLocation;
}
