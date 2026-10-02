package com.uop.capstone.controller;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.uop.capstone.dto.ServiceLocationRequest;
import com.uop.capstone.dto.ServiceType;
import com.uop.capstone.model.ServiceLocation;
import com.uop.capstone.service.ServiceLocationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ServiceLocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceLocationService service;

    @InjectMocks
    private ServiceLocationController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCreateLocation() throws Exception {
        String jsonPayload = """
                {
                    "name": "Health Clinic",
                    "description": "Community health center",
                    "type": "MEDICAL",
                    "address": "123 Main St",
                    "latitude": 37.7749,
                    "longitude": -122.4194,
                    "phone": "555-0199",
                    "email": "info@clinic.org",
                    "website": "https://clinic.org"
                }
                """;

        ServiceLocation savedLocation = new ServiceLocation();
        savedLocation.setName("Health Clinic");
        savedLocation.setDescription("Community health center");
        savedLocation.setType(ServiceType.MEDICAL);

        when(service.addLocation(any(ServiceLocationRequest.class))).thenReturn(savedLocation);

        mockMvc.perform(post("/capstone/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Health Clinic"))
                .andExpect(jsonPath("$.description").value("Community health center"));

        verify(service).addLocation(any(ServiceLocationRequest.class));
    }

    @Test
    void testGetAllLocations() throws Exception {
        ServiceLocation location = new ServiceLocation();
        location.setName("Food Shelter");

        when(service.getAllLocations()).thenReturn(List.of(location));

        mockMvc.perform(get("/capstone/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Food Shelter"));

        verify(service).getAllLocations();
    }

    @Test
    void testGetLocationById() throws Exception {
        ServiceLocation location = new ServiceLocation();
        location.setName("Downtown Clinic");

        when(service.getLocationById(1)).thenReturn(ResponseEntity.ok(location));

        mockMvc.perform(get("/capstone/locations/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Downtown Clinic"));

        verify(service).getLocationById(1);
    }

    @Test
    void testGetLocationByName() throws Exception {
        ServiceLocation location = new ServiceLocation();
        location.setName("Care Center");

        when(service.getLocationByName("Care Center")).thenReturn(ResponseEntity.ok(location));

        mockMvc.perform(get("/capstone/locations/name/Care Center"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Care Center"));

        verify(service).getLocationByName("Care Center");
    }

    @Test
    void testGetLocationByType() throws Exception {
        ServiceLocation location = new ServiceLocation();
        location.setType(ServiceType.MEDICAL);

        doReturn(ResponseEntity.ok(List.of(location))).when(service).getLocationByType("MEDICAL");

        mockMvc.perform(get("/capstone/locations/type/MEDICAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("MEDICAL"));

        verify(service).getLocationByType("MEDICAL");
    }
}
