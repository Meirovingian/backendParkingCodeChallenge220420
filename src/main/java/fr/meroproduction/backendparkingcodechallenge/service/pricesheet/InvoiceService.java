package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice.Invoice;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.invoice.InvoiceRepository;
import fr.meroproduction.backendparkingcodechallenge.service.inout.InOutService;

@Service
public class InvoiceService {

    @Autowired
    private InOutService inOutService;

    @Autowired
    private PriceSheetService priceSheetService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public BigDecimal pay(final Date outgoingDate, final String carRegistration) {
	Assert.notNull(outgoingDate, "An outgoing date must be precised in order to establish charged time");
	Assert.notNull(carRegistration, "A car registration must be communicated to identify the client");

	// I'm searching first for the concerned invoice
	Invoice invoiceInDatabase = getExistingInvoiceByCarRegistration(carRegistration);

	Assert.notNull(invoiceInDatabase, "The initial invoice must be found in order to calculate the due price");

	InOut inOutInDatabase = invoiceInDatabase.getInOut();

	Assert.notNull(inOutInDatabase, "Linked in/out document should exists");

	PriceSheet priceSheetInDatabase = invoiceInDatabase.getPriceSheet();

	Assert.notNull(priceSheetInDatabase, "associated price sheet should exists");

	// Duration calculation between incoming and outgoing dates
	long duration = establishDuration(outgoingDate, inOutInDatabase.getIncomingDate());

	BigDecimal determinedPrice = determinePrice(duration, priceSheetInDatabase, inOutInDatabase.getVehicle());

	// Updating in/out document
	inOutInDatabase.setOutgoingDate(outgoingDate);
	inOutInDatabase.setActivated(false);
	InOut savedInOut = inOutService.save(inOutInDatabase);

	// Updating invoice
	invoiceInDatabase.setInOut(savedInOut);
	invoiceInDatabase.setRoundedTotal(determinedPrice);
	Invoice updatedInvoice = invoiceRepository.save(invoiceInDatabase);

	return updatedInvoice.getRoundedTotal();
    }

    public Invoice createInvoice(Invoice invoice) {
	return invoiceRepository.save(invoice);
    }

    public long establishDuration(Date outgoingDate, Date incomingDate) {
	ZonedDateTime outgoingTime = ZonedDateTime.ofInstant(outgoingDate.toInstant(), ZoneId.systemDefault());
	ZonedDateTime incomingTime = ZonedDateTime.ofInstant(incomingDate.toInstant(), ZoneId.systemDefault());
	return Math.abs(ChronoUnit.MINUTES.between(outgoingTime, incomingTime));
    }

    public long calculateInvoice() {
	ZonedDateTime nowAndHere = now();
	ZonedDateTime fiveHoursSevenMinutesLater = nowAndHere.plusHours(5).plusMinutes(7);

	return Math.abs(ChronoUnit.MINUTES.between(fiveHoursSevenMinutesLater, nowAndHere));
    }

    public long calculateInvoice2() {
	ZonedDateTime nowAndHere = now();
	ZonedDateTime fiveHoursSevenMinutesLater = nowAndHere.plusHours(1).plusMinutes(46);

	return Math.abs(ChronoUnit.MINUTES.between(fiveHoursSevenMinutesLater, nowAndHere));
    }

    public long calculateInvoice3() {
	ZonedDateTime nowAndHere = now();
	ZonedDateTime fiveHoursSevenMinutesLater = nowAndHere.plusHours(7).plusMinutes(27);

	return Math.abs(ChronoUnit.MINUTES.between(fiveHoursSevenMinutesLater, nowAndHere));
    }

    private ZonedDateTime now() {
	LocalDateTime now = LocalDateTime.now();
	return now.atZone(ZoneId.of("Europe/Paris"));
    }

    public BigDecimal determinePrice(final long fullTime, final PriceSheet priceSheet, final Vehicle vehicle) {
	BigDecimal roundedTotal = null;
	if (fullTime >= 0 && priceSheet != null) {
	    final long psFirstBracketMinuteTime = priceSheet.getPsFirstBracketTime().getMinuteDuration();
	    final long psFirstBracketMinuteTimeReferential = priceSheet.getPsFirstBracketTimeRef().getMinuteDuration();
	    final BigDecimal psFirstBracketPrice = priceSheet.getPsFirstBracketPrice();

	    final long psSecondBracketMinuteTimeReferential = priceSheet.getPsSecondBracketTimeRef()
		    .getMinuteDuration();
	    final BigDecimal psSecondBracketPrice = priceSheet.getPsSecondBracketPrice();

	    final long psFreeStartingMinuteTime = priceSheet.getPsFreeTime().getMinuteDuration();

	    final long realSecondBracketTime = fullTime - psFirstBracketMinuteTime;
	    final long realFirstBracketTime = realSecondBracketTime > 0
		    ? psFirstBracketMinuteTime - psFreeStartingMinuteTime
		    : fullTime - psFreeStartingMinuteTime;

	    BigDecimal realFirstBracketPrice = calculatePriceForDuration(realFirstBracketTime,
		    psFirstBracketMinuteTimeReferential, psFirstBracketPrice);

	    BigDecimal realSecondBracketPrice = calculatePriceForDuration(realSecondBracketTime,
		    psSecondBracketMinuteTimeReferential, psSecondBracketPrice);

	    // Total
	    BigDecimal total = realFirstBracketPrice.add(realSecondBracketPrice);

	    BigDecimal calculatedTotal = total;
	    if (vehicle != null) {
		final FuelType fuelType = vehicle.getFuelType();
		if (fuelType != null && fuelType.equals(FuelType.LGP)) {
		    calculatedTotal = calculatedTotal.multiply(priceSheet.getPsLgpCoefficient());
		}
		final VehicleType vehicleType = vehicle.getVehicleType();
		if (vehicleType != null && vehicleType.equals(VehicleType.MOTORCYCLE)) {
		    calculatedTotal = calculatedTotal.multiply(priceSheet.getPsMotorcycleCoefficient());
		}
	    }

	    roundedTotal = roundOnFiftyCents(calculatedTotal);

	}
	return roundedTotal;
    }

    private BigDecimal roundOnFiftyCents(BigDecimal calculatedTotal) {
	BigDecimal roundedTotal;
	int integerPart = calculatedTotal.intValue();
	BigDecimal decimalPart = calculatedTotal.subtract(BigDecimal.valueOf(integerPart));
	BigDecimal finalDecimalPart = BigDecimal.ZERO;
	if (decimalPart.compareTo(BigDecimal.ZERO) > 0) {
	    finalDecimalPart = decimalPart.compareTo(BigDecimal.valueOf(0.5d)) <= 0
		    ? BigDecimal.valueOf(0.5d).setScale(2, RoundingMode.UP)
		    : BigDecimal.ONE.setScale(2, RoundingMode.UP);
	}

	roundedTotal = BigDecimal.valueOf(integerPart).add(finalDecimalPart).setScale(2, RoundingMode.UP);
	return roundedTotal;
    }

    private BigDecimal calculatePriceForDuration(final long bracketDuration, final Long timeReferential,
	    final BigDecimal appliedPrice) {
	if (bracketDuration > 0L && timeReferential > 0L && appliedPrice != null
		&& appliedPrice.compareTo(BigDecimal.ZERO) >= 0) {
	    long divisionOfSecondBracketChargedTime = divide(bracketDuration, timeReferential);
	    long moduloOfSecondBracketChargedTime = modulo(bracketDuration, timeReferential);

	    return calculatePrice(divisionOfSecondBracketChargedTime, moduloOfSecondBracketChargedTime, appliedPrice);
	}
	return BigDecimal.ZERO;
    }

    private BigDecimal calculatePrice(long divisionOfSecondBracketChargedTime, long moduloOfSecondBracketChargedTime,
	    final BigDecimal appliedPrice) {
	BigDecimal realSecondBracketPrice;
	realSecondBracketPrice = appliedPrice != null ? appliedPrice
		.multiply(BigDecimal.valueOf(divisionOfSecondBracketChargedTime)).setScale(2, RoundingMode.UP)
		: BigDecimal.ZERO;

	if (moduloOfSecondBracketChargedTime > 0L) {
	    realSecondBracketPrice = appliedPrice != null
		    ? realSecondBracketPrice.add(appliedPrice).setScale(2, RoundingMode.UP)
		    : realSecondBracketPrice;
	}
	return realSecondBracketPrice;
    }

    /**
     * Function applying dividend modulo divisor. Modulo 0 is undefined so we return
     * 0
     * 
     * @param dividend
     * @param divisor
     * @return
     */
    private long modulo(long dividend, final Long divisor) {
	return divisor > 0L ? dividend % divisor : 0L;
    }

    /**
     * 
     * @param dividend
     * @param divisor
     * @return
     */
    private long divide(final long dividend, final long divisor) {
	return divisor > 0L ? dividend / divisor : 0L;
    }

    public PriceSheetService getPriceSheetService() {
	return priceSheetService;
    }

    public Invoice getExistingInvoiceByCarRegistration(final String carRegistration) {
	Assert.notNull(carRegistration, "A car registration must be communicated to identify the client");
	return invoiceRepository.findInvoiceByCarRegistration(carRegistration);
    }

}
