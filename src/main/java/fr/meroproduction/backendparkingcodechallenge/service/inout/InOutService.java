package fr.meroproduction.backendparkingcodechallenge.service.inout;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice.Invoice;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.user.ParkingUser;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.inout.InOutRepository;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.InvoiceService;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.PriceSheetService;
import fr.meroproduction.backendparkingcodechallenge.service.user.UserService;
import fr.meroproduction.backendparkingcodechallenge.service.vehicle.VehicleService;

@Service
public class InOutService {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PriceSheetService priceSheetService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private InOutRepository inOutRepository;

    public BigDecimal pay(Date outgoingDate, String carRegistration) {
	// Find the last active in out concerned by this car
	InOut inOutInDatabase = inOutRepository.findInOutRelatedToLastActivatedVehicleRegistration(carRegistration);
	if (inOutInDatabase != null) {
	    long duration = invoiceService.establishDuration(outgoingDate, inOutInDatabase.getIncomingDate());
	    BigDecimal determinedPrice = invoiceService.determinePrice(duration,
		    vehicleService.getVehicleByCarRegistration(carRegistration));
	    if (determinedPrice != null) {
		inOutInDatabase.setOutgoingDate(outgoingDate);
		inOutInDatabase.setActivated(false);
		inOutRepository.save(inOutInDatabase);
	    }
	    return determinedPrice;
	}
	return BigDecimal.ZERO;
    }

    public boolean entry(String carRegistration) {
	InOut inOutInDatabase = inOutRepository.findInOutRelatedToLastActivatedVehicleRegistration(carRegistration);
	if (inOutInDatabase == null) {
	    Vehicle foundedVehicle = vehicleService.getVehicleByCarRegistration(carRegistration);
	    ParkingUser user = null;
	    if (foundedVehicle == null) {
		ParkingUser invite = new ParkingUser();
		invite.setUsername("invite");
		invite.setPassword("kjgdfhokdsjgoimjhn");
		invite.setRole("INVITE");
		invite.setBirtDate(new Date());
		invite.setIsEmployee(false);
		user = userService.saveUser(invite);
		// Create a new vehicle associated to a new invited user
		Vehicle car = new Vehicle(VehicleType.CAR, FuelType.GASOLINE);
		car.setCarRegistration(carRegistration);
		car.setUser(user);
		foundedVehicle = vehicleService.saveVehicle(car);
	    }

	    InOut inOut = new InOut();
	    inOut.setUser(foundedVehicle.getUser());
	    inOut.setVehicle(foundedVehicle);
	    inOutRepository.save(inOut);

	    // Initializing an invoice
	    Invoice invoice = new Invoice();
	    invoice.setInOut(inOut);
	    invoice.setPriceSheet(priceSheetService.getLastActivatedPriceSheet());
	    invoiceService.createInvoice(invoice);
	    return true;
	}
	return false;
    }

}
