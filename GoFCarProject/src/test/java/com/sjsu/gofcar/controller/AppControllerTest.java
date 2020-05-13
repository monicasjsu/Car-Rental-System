package com.sjsu.gofcar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import com.sjsu.gofcar.respository.RentalLocationRepository;
import com.sjsu.gofcar.respository.UserDetailsRepository;
import com.sjsu.gofcar.respository.VehicleRepository;
import com.sjsu.gofcar.respository.VehicleTypeRepository;
import com.sjsu.gofcar.service.AppService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author geethu
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AppControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    VehicleTypeRepository vehicleTypeRepository;

    @MockBean
    RentalLocationRepository rentalLocationRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    UserDetailsRepository userDetailsRepository;
    @MockBean
    AppService appService;
    @Autowired
    AppController appController;


    @Test
    public void getAllVehicleType() throws Exception {
        List<VehicleType> allVehicleType = new ArrayList<>();
        when(vehicleTypeRepository.findAll()).thenReturn(allVehicleType);
        Assert.assertNotNull(appController.getAllVehicleType());
    }

    @Test
    public void getAllRentalLocation() throws Exception {
        List<RentalLocation> allRentalLocation = new ArrayList<>();
        when(rentalLocationRepository.findAll()).thenReturn(allRentalLocation);
        Assert.assertNotNull(appController.getAllRentalLocation());
    }

    @Test
    public void getAllVehicles() throws Exception {
        List<Vehicle> allVehicle = new ArrayList<>();
        when(vehicleRepository.findAll()).thenReturn(allVehicle);
        Assert.assertNotNull(appController.getAllVehicles());
    }


}
