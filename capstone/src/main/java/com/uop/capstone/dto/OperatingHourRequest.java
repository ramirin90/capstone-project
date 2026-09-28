package com.uop.capstone.dto;

import java.time.LocalTime;

public record OperatingHourRequest(
        Days days,
        LocalTime openTime,
        LocalTime closeTime
) {}
