package com.uop.capstone.model;

import java.time.LocalTime;

import com.uop.capstone.dto.Days;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OperatingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private Days days;

    private LocalTime openTime;
    private LocalTime closeTime;

    @ManyToOne
    @JoinColumn(name = "service_location_id")
    private ServiceLocation serviceLocation;
}

