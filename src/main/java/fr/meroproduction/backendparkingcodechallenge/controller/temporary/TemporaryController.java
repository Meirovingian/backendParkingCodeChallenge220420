package fr.meroproduction.backendparkingcodechallenge.controller.temporary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.user.ParkingUser;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;
import fr.meroproduction.backendparkingcodechallenge.service.user.UserService;
import fr.meroproduction.backendparkingcodechallenge.service.vehicle.VehicleService;

@RestController
@RequestMapping(path = "/temp")
@CrossOrigin(origins = "${app.request.origin}")
public class TemporaryController {

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    private static final Logger LOGGER = LogManager.getLogger(TemporaryController.class);

    @GetMapping(path = "/insertOnePriceSheet")
    public void insertOnePriceSheet() {
	PriceSheet ps = new PriceSheet();
	ps.setPsFreeStartingMinuteTime(60L);
	ps.setPsFirstBracketMinuteTime(240L);
	ps.setPsFirstBracketMinuteTimeReferential(60L);
	ps.setPsFirstBracketPrice(BigDecimal.valueOf(2d).setScale(2, RoundingMode.UP));
	ps.setPsSecondBracketMinuteTimeReferential(60L);
	ps.setPsSecondBracketPrice(BigDecimal.valueOf(1.5));
	ps.setPsMotorcycleCoefficient(BigDecimal.valueOf(0.5));
	ps.setPsLgpCoefficient(BigDecimal.valueOf(1.07));
	ps.setPsPrintIfNull(false);
	priceSheetRepository.save(ps);
    }

    @GetMapping(path = "/addOneCar")
    public void addOneCar() {
	Vehicle car = new Vehicle(VehicleType.CAR, FuelType.GASOLINE);
	car.setCarRegistration("XX-456-AA");
	Vehicle savedVehicle = vehicleService.saveVehicle(car);
	LOGGER.warn("Saved vehicle identifier: " + savedVehicle.getCarRegistration());
    }

    @GetMapping(path = "/addOneUser")
    public void addOneUser() {
	ParkingUser user = new ParkingUser();
	user.setUsername("Mero");
	user.setPassword("kjgdfhokdsjgoimjhn");
	user.setRole("INVITE");
	user.setBirtDate(new Date());
	user.setIsEmployee(false);
	userService.saveUser(user);
    }

}
