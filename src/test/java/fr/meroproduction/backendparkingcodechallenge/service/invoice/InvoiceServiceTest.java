package fr.meroproduction.backendparkingcodechallenge.service.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice.Invoice;
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

    @Test
    public void testPay() {
	// Data set
	Date incomingDate = new Date();
	ZonedDateTime now = now();
	long duration = timeforce(now, 7, 27);
	String carRegistration = "AA-BBB-CC";
	Invoice mockedInvoice = new Invoice();
	InOut mockedInOut = new InOut();
	mockedInOut.setIncomingDate(incomingDate);
	mockedInvoice.setInOut(mockedInOut);

	// Mocked calls
	when(neededMockedInvoiceRepository.findInvoiceByCarRegistration(carRegistration)).thenReturn(mockedInvoice);

//	invoiceServiceToTest.pay(outgoingDate, carRegistration)
    }

    private long timeforce(ZonedDateTime now, final long hours, final long minutes) {
	ZonedDateTime nowAndHere = now();
	ZonedDateTime fiveHoursSevenMinutesLater = nowAndHere.plusHours(hours).plusMinutes(minutes);
	return Math.abs(ChronoUnit.MINUTES.between(fiveHoursSevenMinutesLater, nowAndHere));
    }

    @Test
    public void testCalculateInvoice() {
	long calculatedInvoice = invoiceServiceToTest.calculateInvoice();
	assertNotNull(calculatedInvoice);
	assertEquals(307L, calculatedInvoice);
    }

//    @Test
//    public void testDeterminePrice() {
//	Vehicle vehicle = new Vehicle();
//	vehicle.setVehicleType(VehicleType.MOTORCYCLE);
//	vehicle.setFuelType(FuelType.GASOLINE);
//
//	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice(), vehicle);
//	BigDecimal expected = BigDecimal.valueOf(5.5d).setScale(2, RoundingMode.UP);
//	assertNotNull(actual);
//	assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testDeterminePrice2() {
//	Vehicle vehicle = new Vehicle();
//	vehicle.setVehicleType(VehicleType.CAR);
//	vehicle.setFuelType(FuelType.GASOLINE);
//
//	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice2(), vehicle);
//	BigDecimal expected = BigDecimal.valueOf(2d).setScale(2, RoundingMode.UP);
//	assertNotNull(actual);
//	assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testDeterminePrice3() {
//	Vehicle vehicle = new Vehicle();
//	vehicle.setVehicleType(VehicleType.CAR);
//	vehicle.setFuelType(FuelType.LGP);
//
//	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice3(), vehicle);
//	BigDecimal expected = BigDecimal.valueOf(18d).setScale(2, RoundingMode.UP);
//	assertNotNull(actual);
//	assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testMotorcycleWithLGPOnFourteenHoursAndFortyTwoMinutes() {
//	// Vehicle data
//	Vehicle vehicle = new Vehicle(VehicleType.MOTORCYCLE, FuelType.LGP);
//
//	// Temporal data
//	final LocalDateTime now = LocalDateTime.now();
//	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(14).plusMinutes(42);
//	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));
//
//	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, vehicle);
//	BigDecimal expected = BigDecimal.valueOf(21d).setScale(2, RoundingMode.UP);
//	assertNotNull(actual);
//	assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testCarWithLGPOnSixHoursAndFiftyNineMinutes() {
//	// Vehicle data
//	Vehicle vehicle = new Vehicle(VehicleType.CAR, FuelType.LGP);
//
//	// Temporal data
//	final LocalDateTime now = LocalDateTime.now();
//	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(6).plusMinutes(59);
//	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));
//
//	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, vehicle);
//	BigDecimal expected = BigDecimal.valueOf(16.5d).setScale(2, RoundingMode.UP);
//	assertNotNull(actual);
//	assertEquals(expected, actual);
//    }

    private ZonedDateTime now() {
	LocalDateTime now = LocalDateTime.now();
	return now.atZone(ZoneId.of("Europe/Paris"));
    }

}
