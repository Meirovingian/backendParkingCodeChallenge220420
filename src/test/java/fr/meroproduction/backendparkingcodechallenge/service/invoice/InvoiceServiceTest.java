package fr.meroproduction.backendparkingcodechallenge.service.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration.ReferentialDuration;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.invoice.InvoiceRepository;
import fr.meroproduction.backendparkingcodechallenge.service.inout.InOutService;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.InvoiceService;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.PriceSheetService;

@SpringBootTest
public class InvoiceServiceTest {

    @Mock
    private InOutService neededMockedInOutService;

    @Mock
    private PriceSheetService neededMockedPriceSheetService;

    @Mock
    private InvoiceRepository neededMockedInvoiceRepository;

    @InjectMocks
    private InvoiceService invoiceServiceToTest;

//    @Test
//    public void testPay() {
//	// Data set
//	Date incomingDate = new Date();
//	ZonedDateTime now = now();
//	long duration = timeforce(now, 7, 27);
//	String carRegistration = "AA-BBB-CC";
//	Invoice mockedInvoice = new Invoice();
//	InOut mockedInOut = new InOut();
//	mockedInOut.setIncomingDate(incomingDate);
//	mockedInvoice.setInOut(mockedInOut);
//
//	// Mocked calls
//	when(neededMockedInvoiceRepository.findInvoiceByCarRegistration(carRegistration)).thenReturn(mockedInvoice);
//
////	invoiceServiceToTest.pay(outgoingDate, carRegistration)
//    }

//    private long timeforce(ZonedDateTime now, final long hours, final long minutes) {
//	ZonedDateTime nowAndHere = now();
//	ZonedDateTime fiveHoursSevenMinutesLater = nowAndHere.plusHours(hours).plusMinutes(minutes);
//	return Math.abs(ChronoUnit.MINUTES.between(fiveHoursSevenMinutesLater, nowAndHere));
//    }

    @Test
    public void testCalculateInvoice() {
	long calculatedInvoice = invoiceServiceToTest.calculateInvoice();
	assertNotNull(calculatedInvoice);
	assertEquals(307L, calculatedInvoice);
    }

    @Test
    public void testDeterminePrice() {
	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleType(VehicleType.MOTORCYCLE);
	vehicle.setFuelType(FuelType.GASOLINE);

	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice(),
		getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(5.5d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testDeterminePrice2() {
	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleType(VehicleType.CAR);
	vehicle.setFuelType(FuelType.GASOLINE);

	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice2(),
		getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(2d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testDeterminePrice3() {
	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleType(VehicleType.CAR);
	vehicle.setFuelType(FuelType.LGP);

	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice3(),
		getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(18d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testMotorcycleWithLGPOnFourteenHoursAndFortyTwoMinutes() {
	// Vehicle data
	Vehicle vehicle = new Vehicle(VehicleType.MOTORCYCLE, FuelType.LGP);

	// Temporal data
	final LocalDateTime now = LocalDateTime.now();
	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(14).plusMinutes(42);
	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));

	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(21d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testCarWithLGPOnSixHoursAndFiftyNineMinutes() {
	// Vehicle data
	Vehicle vehicle = new Vehicle(VehicleType.CAR, FuelType.LGP);

	// Temporal data
	final LocalDateTime now = LocalDateTime.now();
	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(6).plusMinutes(59);
	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));

	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(16.5d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testCarWithGasolineOnFortyFiveHoursAndFourMinutes() {
	// Vehicle data
	Vehicle vehicle = new Vehicle(VehicleType.CAR, FuelType.GASOLINE);

	// Temporal data
	final LocalDateTime now = LocalDateTime.now();
	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(45).plusMinutes(4);
	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));

	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(130.5d).setScale(2);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testCarWithLgpOnFortyFiveHoursAndFourMinutes() {
	// Vehicle data
	Vehicle vehicle = new Vehicle(VehicleType.CAR, FuelType.LGP);

	// Temporal data
	final LocalDateTime now = LocalDateTime.now();
	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(45).plusMinutes(4);
	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));

	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, getPriceSheetExample(), vehicle);
	BigDecimal expected = BigDecimal.valueOf(140d).setScale(2);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

//    private ZonedDateTime now() {
//	LocalDateTime now = LocalDateTime.now();
//	return now.atZone(ZoneId.of("Europe/Paris"));
//    }

    private PriceSheet getPriceSheetExample() {
	PriceSheet priceSheet = new PriceSheet();
	ReferentialDuration thirtyMinutesRef = new ReferentialDuration(30L, "30 minutes", "30 minutes", true);
	ReferentialDuration sixtyMinutesRef = new ReferentialDuration(60L, "1 heure", "1 heure", true);
	ReferentialDuration fourHoursRef = new ReferentialDuration(240L, "240 minutes", "4 heures", false);
	priceSheet.setPsFreeTime(sixtyMinutesRef);
	priceSheet.setPsFirstBracketTime(fourHoursRef);
	priceSheet.setPsFirstBracketTimeRef(sixtyMinutesRef);
	priceSheet.setPsFirstBracketPrice(BigDecimal.valueOf(2d).setScale(2));
	priceSheet.setPsSecondBracketTimeRef(thirtyMinutesRef);
	priceSheet.setPsSecondBracketPrice(BigDecimal.valueOf(1.5).setScale(2));
	priceSheet.setPsMotorcycleCoefficient(BigDecimal.valueOf(0.5).setScale(2));
	priceSheet.setPsLgpCoefficient(BigDecimal.valueOf(1.07).setScale(2));
	priceSheet.setPsPrintIfNull(false);
	return priceSheet;
    }

}
