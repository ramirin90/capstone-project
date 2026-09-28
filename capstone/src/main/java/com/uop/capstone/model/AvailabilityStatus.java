package com.uop.capstone.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AvailabilityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // e.g., "Beds Available", "Full", "Open", "Closed"

    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "service_location_id")
    private ServiceLocation serviceLocation;
}

