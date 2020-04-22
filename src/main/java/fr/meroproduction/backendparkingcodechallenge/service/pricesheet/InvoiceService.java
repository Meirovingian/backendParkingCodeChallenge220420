package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;

@Service
public class InvoiceService {

    @Autowired
    private PriceSheetService priceSheetService;

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
	if (fullTime > 0 && priceSheet != null) {
	    final Long psFirstBracketMinuteTime = priceSheet.getPsFirstBracketMinuteTime();
	    final Long psFirstBracketMinuteTimeReferential = priceSheet.getPsFirstBracketMinuteTimeReferential();
	    final BigDecimal psFirstBracketPrice = priceSheet.getPsFirstBracketPrice();

	    final Long psSecondBracketMinuteTimeReferential = priceSheet.getPsSecondBracketMinuteTimeReferential();
	    final BigDecimal psSecondBracketPrice = priceSheet.getPsSecondBracketPrice();

	    final Long psFreeStartingMinuteTime = priceSheet.getPsFreeStartingMinuteTime();

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

    private long modulo(long realSecondBracketTime, final Long modulo) {
	return realSecondBracketTime % modulo;
    }

    /**
     * 
     * @param dividend
     * @param divider
     * @return
     */
    private long divide(final long dividend, final long divider) {
	return divider > 0L ? dividend / divider : 0L;
    }

    public PriceSheetService getPriceSheetService() {
	return priceSheetService;
    }
}
