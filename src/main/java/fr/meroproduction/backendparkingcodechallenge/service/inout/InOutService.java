package fr.meroproduction.backendparkingcodechallenge.service.inout;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.inout.InOutDTO;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice.Invoice;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
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

    private static final String DASH_SYMBOL = "-";

    public InOut save(InOut entity) {
	return inOutRepository.save(entity);
    }

    public BigDecimal pay(final Date outgoingDate, final String carRegistration) {
	Assert.notNull(outgoingDate, "An outgoing date must be precised in order to establish charged time");
	Assert.notNull(carRegistration, "A car registration must be communicated to identify the client");

	// I'm searching first for the concerned invoice
	Invoice invoiceInDatabase = invoiceService.getExistingInvoiceByCarRegistration(carRegistration);

	Assert.notNull(invoiceInDatabase, "The initial invoice must be found in order to calculate the due price");

	InOut inOutInDatabase = invoiceInDatabase.getInOut();

	Assert.notNull(inOutInDatabase, "Linked in/out document should exists");

	PriceSheet priceSheetInDatabase = invoiceInDatabase.getPriceSheet();

	Assert.notNull(priceSheetInDatabase, "associated price sheet should exists");

	// Duration calculation between incoming and outgoing dates
	long duration = invoiceService.establishDuration(outgoingDate, inOutInDatabase.getIncomingDate());

	BigDecimal determinedPrice = invoiceService.determinePrice(duration, priceSheetInDatabase,
		inOutInDatabase.getVehicle());

	// Updating in/out document
	inOutInDatabase.setOutgoingDate(outgoingDate);
	inOutInDatabase.setActivated(false);
	InOut savedInOut = inOutRepository.save(inOutInDatabase);

	// Updating invoice
	invoiceInDatabase.setInOut(savedInOut);
	invoiceInDatabase.setRoundedTotal(determinedPrice);

	return determinedPrice;
    }

    public boolean entry(InOutDTO entrance) {
	Assert.notNull(entrance, "Entrance informations are required");
	StringBuilder carRegistration = new StringBuilder();
	carRegistration.append(entrance.getCarRegistrationPartOne());
	carRegistration.append(DASH_SYMBOL);
	carRegistration.append(entrance.getCarRegistrationPartTwo());
	carRegistration.append(DASH_SYMBOL);
	carRegistration.append(entrance.getCarRegistrationPartThree());
	InOut inOutInDatabase = inOutRepository
		.findInOutRelatedToLastActivatedVehicleRegistration(carRegistration.toString());
	if (inOutInDatabase == null) {
	    Vehicle foundedVehicle = vehicleService.getVehicleByCarRegistration(carRegistration.toString());
	    ParkingUser user = null;
	    if (foundedVehicle == null) {
		ParkingUser invite = new ParkingUser();
		invite.setUsername("invite");
		invite.setPassword("fictive");
		invite.setRole("INVITE");
		invite.setBirtDate(new Date());
		invite.setIsEmployee(false);
		user = userService.saveUser(invite);
		// Create a new vehicle associated to a new invited user
		Vehicle car = new Vehicle(VehicleType.CAR, FuelType.GASOLINE);
		car.setCarRegistration(carRegistration.toString());
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
	    invoice.setPriceSheet(priceSheetService.getSpecificEntity(entrance.getPriceSheetId()));
	    invoiceService.createInvoice(invoice);
	    return true;
	}
	return false;
    }

}
