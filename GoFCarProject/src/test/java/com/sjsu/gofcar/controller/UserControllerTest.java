package com.sjsu.gofcar.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.gofcar.model.Address;
import com.sjsu.gofcar.model.reservation.Reservation;
import com.sjsu.gofcar.model.reservation.ReservationRequest;
import com.sjsu.gofcar.model.reservation.ReservationStatus;
import com.sjsu.gofcar.model.user.UserDetails;
import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import com.sjsu.gofcar.respository.RentalLocationRepository;
import com.sjsu.gofcar.respository.ReservationRepository;
import com.sjsu.gofcar.respository.UserDetailsRepository;
import com.sjsu.gofcar.respository.VehicleRepository;
import com.sjsu.gofcar.respository.VehicleTypeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private UserDetailsRepository userDetailsRepository;

  @MockBean
  private VehicleRepository vehicleRepository;

  @MockBean
  private VehicleTypeRepository vehicleTypeRepository;

  @MockBean
  private RentalLocationRepository rentalLocationRepository;

  @MockBean
  private ReservationRepository reservationRepository;

  @Autowired
  private UserController userController;

  private String userDetails = "{\n"
      + "    \"email\": \"monica.dommaraju@gmail.com\",\n"
      + "    \"password\": \"$2a$10$LtFcBBKlqL/MwiqJmxnOPOl5zSffJ0FCkCy50pV4Qv.ylM0C31Q26\",\n"
      + "    \"firstName\": \"Moni\",\n"
      + "    \"lastName\": \"D\",\n"
      + "    \"age\": 25,\n"
      + "    \"address\": {\n"
      + "        \"streetName\": \"825 East Evelyn Ave\",\n"
      + "        \"city\": \"Sunnyvale\",\n"
      + "        \"state\": \"California\",\n"
      + "        \"zipCode\": 94040\n"
      + "    },\n"
      + "    \"license\": {\n"
      + "        \"licenseId\": null,\n"
      + "        \"expiry\": \"2022-09-29T00:00:00.000+0000\",\n"
      + "        \"type\": null,\n"
      + "        \"stateOfIssue\": null\n"
      + "    },\n"
      + "    \"card\": {\n"
      + "        \"cardNumber\": null,\n"
      + "        \"dateOfIssue\": null,\n"
      + "        \"expiryDate\": \"2020-09-29T00:00:00.000+0000\",\n"
      + "        \"name\": null,\n"
      + "        \"cvv\": 0\n"
      + "    },\n"
      + "    \"validity\": 1604458676910,\n"
      + "    \"active\": true\n"
      + "}";

  private String vehicleTypes = "[\n"
      + "    {\n"
      + "        \"vehicleTypeId\": 12,\n"
      + "        \"vehicleType\": \"compact\",\n"
      + "        \"hourlyPrice\": 15.0,\n"
      + "        \"lateReturnFee\": 50.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleTypeId\": 77,\n"
      + "        \"vehicleType\": \"intermediate\",\n"
      + "        \"hourlyPrice\": 25.0,\n"
      + "        \"lateReturnFee\": 80.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleTypeId\": 29,\n"
      + "        \"vehicleType\": \"sedan\",\n"
      + "        \"hourlyPrice\": 50.0,\n"
      + "        \"lateReturnFee\": 10.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleTypeId\": 19,\n"
      + "        \"vehicleType\": \"comfort\",\n"
      + "        \"hourlyPrice\": 20.0,\n"
      + "        \"lateReturnFee\": 100.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleTypeId\": 84,\n"
      + "        \"vehicleType\": \"full\",\n"
      + "        \"hourlyPrice\": 50.0,\n"
      + "        \"lateReturnFee\": 200.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleTypeId\": 40,\n"
      + "        \"vehicleType\": \"suv\",\n"
      + "        \"hourlyPrice\": 0.0,\n"
      + "        \"lateReturnFee\": 0.0\n"
      + "    }\n"
      + "]";

  private String rentalLocations = "[\n"
      + "    {\n"
      + "        \"rentalLocationId\": 981,\n"
      + "        \"locationName\": \"GoF-Rent-A-Car Mountain View\",\n"
      + "        \"address\": {\n"
      + "            \"streetName\": \"99 El Camino Real\",\n"
      + "            \"city\": \"Mountain View\",\n"
      + "            \"state\": \"California\",\n"
      + "            \"zipCode\": 94041\n"
      + "        },\n"
      + "        \"vehicleCapacity\": 50,\n"
      + "        \"vehicles\": [\n"
      + "            7644,\n"
      + "            6108\n"
      + "        ]\n"
      + "    },\n"
      + "    {\n"
      + "        \"rentalLocationId\": 163,\n"
      + "        \"locationName\": \"GoF-Rent-A-Car Palo Alto\",\n"
      + "        \"address\": {\n"
      + "            \"streetName\": \"680 Corpus St\",\n"
      + "            \"city\": \"Palo Alto\",\n"
      + "            \"state\": \"California\",\n"
      + "            \"zipCode\": 93341\n"
      + "        },\n"
      + "        \"vehicleCapacity\": 20,\n"
      + "        \"vehicles\": [\n"
      + "            6774,\n"
      + "            9549\n"
      + "        ]\n"
      + "    },\n"
      + "    {\n"
      + "        \"rentalLocationId\": 475,\n"
      + "        \"locationName\": \"GoF-Rent-A-Car Sunnyvale\",\n"
      + "        \"address\": {\n"
      + "            \"streetName\": \"110 Saratago Ave\",\n"
      + "            \"city\": \"Sunnyvale\",\n"
      + "            \"state\": \"California\",\n"
      + "            \"zipCode\": 90188\n"
      + "        },\n"
      + "        \"vehicleCapacity\": 35,\n"
      + "        \"vehicles\": [\n"
      + "            6293,\n"
      + "            7812\n"
      + "        ]\n"
      + "    },\n"
      + "    {\n"
      + "        \"rentalLocationId\": 573,\n"
      + "        \"locationName\": \"San Jose\",\n"
      + "        \"address\": {\n"
      + "            \"streetName\": \"Park View Dr\",\n"
      + "            \"city\": \"San Jose\",\n"
      + "            \"state\": \"CA\",\n"
      + "            \"zipCode\": 323232\n"
      + "        },\n"
      + "        \"vehicleCapacity\": 12,\n"
      + "        \"vehicles\": null\n"
      + "    }\n"
      + "]";

  private String vehicles = "[\n"
      + "    {\n"
      + "        \"vehicleId\": 6108,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 12,\n"
      + "            \"vehicleType\": \"compact\",\n"
      + "            \"hourlyPrice\": 15.0,\n"
      + "            \"lateReturnFee\": 50.0\n"
      + "        },\n"
      + "        \"make\": \"Toyota New\",\n"
      + "        \"model\": \"Corolla\",\n"
      + "        \"year\": 2019,\n"
      + "        \"registrationTag\": \"XYZ990\",\n"
      + "        \"currentMileage\": 3144.5,\n"
      + "        \"vehicleCondition\": \"bad\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 981\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 7644,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 12,\n"
      + "            \"vehicleType\": \"compact\",\n"
      + "            \"hourlyPrice\": 15.0,\n"
      + "            \"lateReturnFee\": 50.0\n"
      + "        },\n"
      + "        \"make\": \"Ford\",\n"
      + "        \"model\": \"Fusion\",\n"
      + "        \"year\": 2017,\n"
      + "        \"registrationTag\": \"PQR788\",\n"
      + "        \"currentMileage\": 5400.0,\n"
      + "        \"vehicleCondition\": \"EXCELLENT\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 981\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 4028,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 77,\n"
      + "            \"vehicleType\": \"intermediate\",\n"
      + "            \"hourlyPrice\": 25.0,\n"
      + "            \"lateReturnFee\": 80.0\n"
      + "        },\n"
      + "        \"make\": \"Chevrolet\",\n"
      + "        \"model\": \"Cruze\",\n"
      + "        \"year\": 2018,\n"
      + "        \"registrationTag\": \"19MNP0\",\n"
      + "        \"currentMileage\": 2498.1,\n"
      + "        \"vehicleCondition\": \"EXCELLENT\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 393\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 6036,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 84,\n"
      + "            \"vehicleType\": \"full\",\n"
      + "            \"hourlyPrice\": 50.0,\n"
      + "            \"lateReturnFee\": 200.0\n"
      + "        },\n"
      + "        \"make\": \"Toyota\",\n"
      + "        \"model\": \"Camry\",\n"
      + "        \"year\": 2019,\n"
      + "        \"registrationTag\": \"LKM990\",\n"
      + "        \"currentMileage\": 5689.3,\n"
      + "        \"vehicleCondition\": \"EXCELLENT\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 393\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 3130,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 77,\n"
      + "            \"vehicleType\": \"intermediate\",\n"
      + "            \"hourlyPrice\": 25.0,\n"
      + "            \"lateReturnFee\": 80.0\n"
      + "        },\n"
      + "        \"make\": \"Nissan\",\n"
      + "        \"model\": \"Sentra\",\n"
      + "        \"year\": 2019,\n"
      + "        \"registrationTag\": \"HHJ990\",\n"
      + "        \"currentMileage\": 1200.3,\n"
      + "        \"vehicleCondition\": \"EXCELLENT\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 393\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 6293,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 77,\n"
      + "            \"vehicleType\": \"intermediate\",\n"
      + "            \"hourlyPrice\": 25.0,\n"
      + "            \"lateReturnFee\": 80.0\n"
      + "        },\n"
      + "        \"make\": \"Nissan\",\n"
      + "        \"model\": \"Altima\",\n"
      + "        \"year\": 2018,\n"
      + "        \"registrationTag\": \"ZA01W\",\n"
      + "        \"currentMileage\": 10000.3,\n"
      + "        \"vehicleCondition\": \"EXCELLENT\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 475\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 6774,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 40,\n"
      + "            \"vehicleType\": \"suv\",\n"
      + "            \"hourlyPrice\": 0.0,\n"
      + "            \"lateReturnFee\": 0.0\n"
      + "        },\n"
      + "        \"make\": \"Honda\",\n"
      + "        \"model\": \"CRV\",\n"
      + "        \"year\": 2018,\n"
      + "        \"registrationTag\": \"TGQQ90\",\n"
      + "        \"currentMileage\": 25000.3,\n"
      + "        \"vehicleCondition\": \"GOOD\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 163\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 9549,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 40,\n"
      + "            \"vehicleType\": \"suv\",\n"
      + "            \"hourlyPrice\": 0.0,\n"
      + "            \"lateReturnFee\": 0.0\n"
      + "        },\n"
      + "        \"make\": \"Audi\",\n"
      + "        \"model\": \"Q7\",\n"
      + "        \"year\": 2018,\n"
      + "        \"registrationTag\": \"NNMH65\",\n"
      + "        \"currentMileage\": 13879.9,\n"
      + "        \"vehicleCondition\": \"GOOD\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 163\n"
      + "    },\n"
      + "    {\n"
      + "        \"vehicleId\": 7812,\n"
      + "        \"type\": {\n"
      + "            \"vehicleTypeId\": 19,\n"
      + "            \"vehicleType\": \"comfort\",\n"
      + "            \"hourlyPrice\": 20.0,\n"
      + "            \"lateReturnFee\": 100.0\n"
      + "        },\n"
      + "        \"make\": \"Lexus\",\n"
      + "        \"model\": \"NX\",\n"
      + "        \"year\": 2017,\n"
      + "        \"registrationTag\": \"TRG56H\",\n"
      + "        \"currentMileage\": 33989.9,\n"
      + "        \"vehicleCondition\": \"EXCELLENT\",\n"
      + "        \"vehicleStatus\": \"AVAILABLE\",\n"
      + "        \"rentalLocationId\": 475\n"
      + "    }\n"
      + "]";


  private String reservations = "[\n"
      + "    {\n"
      + "       \"reservationId\": \"f79b777c-ddee-45ee-ab29-5e8649360992\",\n"
      + "       \"vehicleId\": 6108,\n"
      + "       \"durationHours\": 5,\n"
      + "       \"timeStamp\": 1588658222652,\n"
      + "       \"reserveFrom\": 1588709301182,\n"
      + "       \"userId\": \"moni.d@gmail.com\",\n"
      + "       \"reservationStatus\": \"RESERVED\",\n"
      + "       \"estimatedCharges\": 75.0,\n"
      + "       \"finalCharges\": 0.0,\n"
      + "       \"lateFee\": 0.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"reservationId\": \"f79b777c-ddee-45ee-ab29-5e8649360992\",\n"
      + "        \"vehicleId\": 6108,\n"
      + "        \"durationHours\": 5,\n"
      + "        \"timeStamp\": 1588658222652,\n"
      + "        \"reserveFrom\": 1588709301182,\n"
      + "        \"userId\": \"moni.d@gmail.com\",\n"
      + "        \"reservationStatus\": \"RESERVED\",\n"
      + "        \"estimatedCharges\": 75.0,\n"
      + "        \"finalCharges\": 0.0,\n"
      + "        \"lateFee\": 0.0\n"
      + "    },\n"
      + "    {\n"
      + "        \"reservationId\": \"80374a51-bf43-473e-93af-20c6034f1b4b\",\n"
      + "        \"vehicleId\": 6108,\n"
      + "        \"durationHours\": 1,\n"
      + "        \"timeStamp\": 1588733772001,\n"
      + "        \"reserveFrom\": 1588509301182,\n"
      + "        \"userId\": \"moni.d@gmail.com\",\n"
      + "        \"reservationStatus\": \"RESERVED\",\n"
      + "        \"estimatedCharges\": 15.0,\n"
      + "        \"finalCharges\": 0.0,\n"
      + "        \"lateFee\": 0.0\n"
      + "    }\n"
      + "]";

  @Test
  public void getVehicleType() throws Exception {
    when(vehicleTypeRepository.findAll())
        .thenReturn(mapper.readValue(vehicleTypes, new TypeReference<List<VehicleType>>(){}));

    this.mockMvc.perform(get("/api/user/getVehicleTypes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(vehicleTypes));
  }

  @Test
  public void getRentalLocation() throws Exception {
    when(rentalLocationRepository.findAll())
        .thenReturn(mapper.readValue(rentalLocations, new TypeReference<List<RentalLocation>>(){}));

    this.mockMvc.perform(get("/api/user/getRentalLocations"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(rentalLocations));
  }

  @Test
  public void getVehicles() throws Exception {
    when(vehicleRepository.findAll())
        .thenReturn(mapper.readValue(vehicles, new TypeReference<List<Vehicle>>(){}));

    this.mockMvc.perform(get("/api/user/getVehicles"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(vehicles));
  }

  @Test
  public void returnVehicle() {

  }

  @Test
  public void getUserDetails() throws Exception {
    UserDetails userDetailsObj = mapper.readValue(userDetails, UserDetails.class);
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetailsObj));

    Claims claims = new DefaultClaims();
    claims.setSubject("monica.dommaraju@gmail.com");

    assertThat(userController.getUserDetails(claims)).isEqualTo(userDetailsObj);
  }

  @Test
  public void modify() throws Exception {
    UserDetails userDetailsObj = mapper.readValue(userDetails, UserDetails.class);
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetailsObj));

    when(userDetailsRepository.save(any(UserDetails.class)))
        .thenReturn(userDetailsObj.toBuilder()
            .address(Address.builder()
                .city("Sunnyvale")
                .build()
            )
            .build()
        );

    Claims claims = new DefaultClaims();
    claims.setSubject("monica.dommaraju@gmail.com");

    this.mockMvc.perform(post("/api/user/modify")
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("claims", claims)
        .content("{\n"
            + "    \"phone\": \"9195921231\",\n"
            + "    \"address\": {\n"
            + "        \"streetName\": \"825 E Evelyn Ave\",\n"
            + "        \"city\": \"Sunnyvale\",\n"
            + "        \"state\": \"California\",\n"
            + "        \"zipCode\": 94040\n"
            + "    },\n"
            + "    \"license\": {\n"
            + "        \"expiry\": \"2022-09-29\"\n"
            + "    },\n"
            + "    \"card\": {\n"
            + "        \"expiryDate\": \"2020-09-29\"\n"
            + "    }\n"
            + "}"
        ))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.address.city", is("Sunnyvale")));
  }

  @Test
  public void deactivate() throws Exception {
    UserDetails userDetailsObj = mapper.readValue(userDetails, UserDetails.class);
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetailsObj));

    when(userDetailsRepository.save(any(UserDetails.class)))
        .thenReturn(userDetailsObj);

    Claims claims = new DefaultClaims();
    claims.setSubject("monica.dommaraju@gmail.com");

    this.mockMvc.perform(get("/api/user/deactivate")
        .requestAttr("claims", claims))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void getVehicleActiveReservationsByEpoch() throws Exception {
    when(reservationRepository.findAllByReservationStatusAndVehicleId(eq(ReservationStatus.RESERVED), anyInt()))
        .thenReturn(mapper.readValue(reservations, new TypeReference<List<Reservation>>(){}));

    this.mockMvc.perform(get("/api/user/checkAvailability")
        .param("vehicleId", "6108"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getVehicleActiveReservationsByDateTime() throws Exception {
    when(reservationRepository.findAllByReservationStatusAndVehicleId(eq(ReservationStatus.RESERVED), anyInt()))
        .thenReturn(mapper.readValue(reservations, new TypeReference<List<Reservation>>(){}));

    this.mockMvc.perform(get("/api/user/checkAvailabilityDateTime")
        .param("vehicleId", "6108"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getUserReservations() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setSubject("monica.dommaraju@gmail.com");

    when(reservationRepository.findAllByUserIdAndReservationStatus(anyString(), eq(ReservationStatus.RESERVED)))
        .thenReturn(mapper.readValue(reservations, new TypeReference<List<Reservation>>(){}));

    this.mockMvc.perform(get("/api/user/getReservations")
        .param("status", "RESERVED")
        .requestAttr("claims", claims))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(reservations));
  }

  @Test
  public void reserveVehicle() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setSubject("monica.dommaraju@gmail.com");

    when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(
        Vehicle.builder()
            .make("Mazda")
            .model("6")
            .type(VehicleType.builder()
                .hourlyPrice(10)
                .hourly6To12HoursPrice(8.0)
                .lateReturnFee(20)
                .build()
            ).build()
        ));


    List<Reservation> bookings = mapper.readValue(reservations, new TypeReference<List<Reservation>>(){});
    when(reservationRepository.save(any(Reservation.class)))
        .thenReturn(bookings.get(0));

    when(reservationRepository.findAllByReservationStatusAndVehicleId(eq(ReservationStatus.RESERVED), anyInt()))
        .thenReturn(bookings);

    ReservationRequest reservationRequest = ReservationRequest.builder()
        .durationHours(10)
        .fromTime(DateTime.now().plusDays(4).getMillis())
        .vehicleId(123)
        .build();

    this.mockMvc.perform(
          post("/api/user/reserveVehicle")
          .contentType(MediaType.APPLICATION_JSON)
          .requestAttr("claims", claims)
          .content(mapper.writeValueAsString(reservationRequest))
          .requestAttr("claims", claims)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(bookings.get(0))));
  }

  @Test
  public void cancelReservation() throws Exception {
    List<Reservation> bookings = mapper.readValue(reservations, new TypeReference<List<Reservation>>(){});
    when(reservationRepository.findById(anyString()))
        .thenReturn(Optional.of(bookings.get(0)));

    when(vehicleRepository.findById(anyInt())).thenReturn(Optional.of(
        Vehicle.builder()
            .make("Mazda")
            .model("6")
            .type(VehicleType.builder()
                .hourlyPrice(10)
                .lateReturnFee(20)
                .build()
            ).build()));

    this.mockMvc.perform(get("/api/user/cancelReservation")
        .param("id", "f79b777c-ddee-45ee-ab29-5e8649360992"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}