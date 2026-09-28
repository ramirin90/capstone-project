package com.uop.capstone.dto;

import java.util.List;

import lombok.Data;

@Data
public record ServiceLocationRequest(
        String name,
        String description,
        ServiceType type,
        String address,
        Double latitude,
        Double longitude,
        String phone,
        String email,
        String website,
        List<OperatingHourRequest> operatingHours,
        List<String> tags
) {}
