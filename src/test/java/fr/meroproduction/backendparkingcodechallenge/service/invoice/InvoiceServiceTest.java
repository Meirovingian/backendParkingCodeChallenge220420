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
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.InvoiceService;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.PriceSheetService;

@SpringBootTest
public class InvoiceServiceTest {

    @InjectMocks
    private InvoiceService invoiceServiceToTest;

    @Mock
    private PriceSheetService neededMockedPriceSheetService;

    @Test
    public void testCalculateInvoice() {
	long calculatedInvoice = invoiceServiceToTest.calculateInvoice();
	assertNotNull(calculatedInvoice);
	assertEquals(307L, calculatedInvoice);
    }

    @Test
    public void testDeterminePrice() {
	PriceSheet priceSheet = getPriceSheetExample();

	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleType(VehicleType.MOTORCYCLE);
	vehicle.setFuelType(FuelType.GASOLINE);

	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice(), priceSheet,
		vehicle);
	BigDecimal expected = BigDecimal.valueOf(5.5d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testDeterminePrice2() {
	PriceSheet priceSheet = getPriceSheetExample();

	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleType(VehicleType.CAR);
	vehicle.setFuelType(FuelType.GASOLINE);

	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice2(), priceSheet,
		vehicle);
	BigDecimal expected = BigDecimal.valueOf(2d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testDeterminePrice3() {
	PriceSheet priceSheet = getPriceSheetExample();

	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleType(VehicleType.CAR);
	vehicle.setFuelType(FuelType.LGP);

	BigDecimal actual = invoiceServiceToTest.determinePrice(invoiceServiceToTest.calculateInvoice3(), priceSheet,
		vehicle);
	BigDecimal expected = BigDecimal.valueOf(18d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testMotorcycleWithLGPOnFourteenHoursAndFortyTwoMinutes() {
	// Same price sheet as usual
	PriceSheet priceSheet = getPriceSheetExample();

	// Vehicle data
	Vehicle vehicle = new Vehicle(VehicleType.MOTORCYCLE, FuelType.LGP);

	// Temporal data
	final LocalDateTime now = LocalDateTime.now();
	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(14).plusMinutes(42);
	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));

	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, priceSheet, vehicle);
	BigDecimal expected = BigDecimal.valueOf(21d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void testCarWithLGPOnSixHoursAndFiftyNineMinutes() {
	// Same price sheet as usual
	PriceSheet priceSheet = getPriceSheetExample();

	// Vehicle data
	Vehicle vehicle = new Vehicle(VehicleType.CAR, FuelType.LGP);

	// Temporal data
	final LocalDateTime now = LocalDateTime.now();
	final ZonedDateTime nowAndHere = now.atZone(ZoneId.of("Europe/Paris")).plusHours(6).plusMinutes(59);
	final long duration = Math.abs(ChronoUnit.MINUTES.between(now, nowAndHere));

	BigDecimal actual = invoiceServiceToTest.determinePrice(duration, priceSheet, vehicle);
	BigDecimal expected = BigDecimal.valueOf(16.5d).setScale(2, RoundingMode.UP);
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    private PriceSheet getPriceSheetExample() {
	PriceSheet priceSheet = new PriceSheet();
	priceSheet.setPsFreeStartingMinuteTime(60L);
	priceSheet.setPsFirstBracketMinuteTime(240L);
	priceSheet.setPsFirstBracketMinuteTimeReferential(60L);
	priceSheet.setPsFirstBracketPrice(BigDecimal.valueOf(2d));
	priceSheet.setPsSecondBracketMinuteTimeReferential(30L);
	priceSheet.setPsSecondBracketPrice(BigDecimal.valueOf(1.5));
	priceSheet.setPsMotorcycleCoefficient(BigDecimal.valueOf(0.5));
	priceSheet.setPsLgpCoefficient(BigDecimal.valueOf(1.07));
	priceSheet.setPsPrintIfNull(false);
	return priceSheet;
    }

}
