package com.uop.capstone.service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uop.capstone.dto.Days;
import com.uop.capstone.dto.OperatingHourRequest;
import com.uop.capstone.dto.ServiceLocationRequest;
import com.uop.capstone.dto.ServiceType;
import com.uop.capstone.model.ServiceLocation;
import com.uop.capstone.repository.ServiceLocationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceLocationServiceTest {

    @Mock
    private ServiceLocationRepository repository;

    @InjectMocks
    private ServiceLocationService service;

    private ServiceLocation sampleLocation;
    private ServiceLocationRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleLocation = new ServiceLocation();
        sampleLocation.setId(1L);
        sampleLocation.setName("Community Health Center");
        sampleLocation.setDescription("Provides general healthcare services");
        sampleLocation.setType(ServiceType.MEDICAL);
        sampleLocation.setAddress("123 Main St");
        sampleLocation.setLatitude(37.7749);
        sampleLocation.setLongitude(-122.4194);
        sampleLocation.setPhone("555-0100");
        sampleLocation.setEmail("contact@healthcenter.org");
        sampleLocation.setWebsite("https://healthcenter.org");

        OperatingHourRequest hourRequest = new OperatingHourRequest(
                Days.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );

        sampleRequest = new ServiceLocationRequest(
                "Community Health Center",
                "Provides general healthcare services",
                ServiceType.MEDICAL,
                "123 Main St",
                37.7749,
                -122.4194,
                "555-0100",
                "contact@healthcenter.org",
                "https://healthcenter.org",
                List.of(hourRequest),
                List.of("Clinic", "Free Services")
        );
    }

    @Test
    @DisplayName("addLocation - Should successfully save and return location with hours and tags")
    void testAddLocation_Success() {
        when(repository.save(any(ServiceLocation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ServiceLocation created = service.addLocation(sampleRequest);

        assertNotNull(created);
        assertEquals("Community Health Center", created.getName());
        assertEquals("Provides general healthcare services", created.getDescription());
        assertEquals(ServiceType.MEDICAL, created.getType());
        assertEquals("123 Main St", created.getAddress());
        assertEquals(37.7749, created.getLatitude());
        assertEquals(-122.4194, created.getLongitude());
        assertEquals("555-0100", created.getPhone());
        assertEquals("contact@healthcenter.org", created.getEmail());
        assertEquals("https://healthcenter.org", created.getWebsite());

        // Verify operating hours mapping
        assertNotNull(created.getOperatingHours());
        assertEquals(1, created.getOperatingHours().size());
        assertEquals(Days.MONDAY, created.getOperatingHours().get(0).getDays());
        assertEquals(LocalTime.of(9, 0), created.getOperatingHours().get(0).getOpenTime());
        assertEquals(LocalTime.of(17, 0), created.getOperatingHours().get(0).getCloseTime());
        assertEquals(created, created.getOperatingHours().get(0).getServiceLocation());

        // Verify tags mapping
        assertNotNull(created.getTags());
        assertEquals(2, created.getTags().size());
        assertEquals("Clinic", created.getTags().get(0).getLabel());
        assertEquals("Free Services", created.getTags().get(1).getLabel());
        assertEquals(created, created.getTags().get(0).getServiceLocation());

        verify(repository, times(1)).save(any(ServiceLocation.class));
    }

    @Test
    @DisplayName("getAllLocations - Should return list of all locations")
    void testGetAllLocations() {
        when(repository.findAll()).thenReturn(List.of(sampleLocation));

        List<ServiceLocation> result = service.getAllLocations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Community Health Center", result.get(0).getName());

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("getLocationById - Should return 200 OK when location exists")
    void testGetLocationById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleLocation));

        ResponseEntity<ServiceLocation> response = service.getLocationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Community Health Center", response.getBody().getName());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getLocationById - Should return 404 Not Found when location does not exist")
    void testGetLocationById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<ServiceLocation> response = service.getLocationById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("getLocationByName - Should return 200 OK when location exists")
    void testGetLocationByName_Found() {
        when(repository.findByName("Community Health Center")).thenReturn(Optional.of(sampleLocation));

        ResponseEntity<ServiceLocation> response = service.getLocationByName("Community Health Center");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Community Health Center", response.getBody().getName());

        verify(repository, times(1)).findByName("Community Health Center");
    }

    @Test
    @DisplayName("getLocationByName - Should return 404 Not Found when location does not exist")
    void testGetLocationByName_NotFound() {
        when(repository.findByName("Nonexistent Clinic")).thenReturn(Optional.empty());

        ResponseEntity<ServiceLocation> response = service.getLocationByName("Nonexistent Clinic");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(repository, times(1)).findByName("Nonexistent Clinic");
    }

    @Test
    @DisplayName("getLocationByType - Should return 200 OK with locations for valid type")
    void testGetLocationByType_Success() {
        when(repository.findByType(ServiceType.MEDICAL)).thenReturn(Optional.of(List.of(sampleLocation)));

        ResponseEntity<?> response = service.getLocationByType("MEDICAL");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List<?>);
        List<?> list = (List<?>) response.getBody();
        assertEquals(1, list.size());

        verify(repository, times(1)).findByType(ServiceType.MEDICAL);
    }

    @Test
    @DisplayName("getLocationByType - Should return 200 OK with lower/mixed case type parameter")
    void testGetLocationByType_CaseInsensitiveType() {
        when(repository.findByType(ServiceType.MEDICAL)).thenReturn(Optional.of(List.of(sampleLocation)));

        ResponseEntity<?> response = service.getLocationByType("medical");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).findByType(ServiceType.MEDICAL);
    }

    @Test
    @DisplayName("getLocationByType - Should return 404 Not Found when no locations exist for type")
    void testGetLocationByType_EmptyResult() {
        when(repository.findByType(ServiceType.MEDICAL)).thenReturn(Optional.of(Collections.emptyList()));

        ResponseEntity<?> response = service.getLocationByType("MEDICAL");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No locations found for type: MEDICAL", response.getBody());

        verify(repository, times(1)).findByType(ServiceType.MEDICAL);
    }

    @Test
    @DisplayName("getLocationByType - Should return 404 Not Found when optional is empty")
    void testGetLocationByType_OptionalEmpty() {
        when(repository.findByType(ServiceType.MEDICAL)).thenReturn(Optional.empty());

        ResponseEntity<?> response = service.getLocationByType("MEDICAL");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No locations found for type: MEDICAL", response.getBody());

        verify(repository, times(1)).findByType(ServiceType.MEDICAL);
    }

    @Test
    @DisplayName("getLocationByType - Should return 400 Bad Request for invalid service type string")
    void testGetLocationByType_InvalidType() {
        ResponseEntity<?> response = service.getLocationByType("INVALID_TYPE");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid type: INVALID_TYPE", response.getBody());

        verifyNoInteractions(repository);
    }
}
