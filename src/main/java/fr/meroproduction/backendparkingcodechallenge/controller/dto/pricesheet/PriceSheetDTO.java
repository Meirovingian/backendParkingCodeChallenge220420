package fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceSheetDTO implements Serializable {

    private long psFreeStartingMinuteTime;

    private long psFirstBracketMinuteTime;

    private long psFirstBracketMinuteTimeReferential;

    private BigDecimal psFirstBracketPrice;

    private long psSecondBracketMinuteTimeReferential;

    private BigDecimal psSecondBracketPrice;

    private BigDecimal psMotorcycleCoefficient;

    private BigDecimal psLgpCoefficient;

    private boolean psPrintIfNull;

    private static final long serialVersionUID = -633201883590850051L;

    public PriceSheetDTO() {
	super();
    }

    public long getPsFreeStartingMinuteTime() {
	return psFreeStartingMinuteTime;
    }

    public void setPsFreeStartingMinuteTime(long psFreeStartingMinuteTime) {
	this.psFreeStartingMinuteTime = psFreeStartingMinuteTime;
    }

    public long getPsFirstBracketMinuteTime() {
	return psFirstBracketMinuteTime;
    }

    public void setPsFirstBracketMinuteTime(long psFirstBracketMinuteTime) {
	this.psFirstBracketMinuteTime = psFirstBracketMinuteTime;
    }

    public long getPsFirstBracketMinuteTimeReferential() {
	return psFirstBracketMinuteTimeReferential;
    }

    public void setPsFirstBracketMinuteTimeReferential(long psFirstBracketMinuteTimeReferential) {
	this.psFirstBracketMinuteTimeReferential = psFirstBracketMinuteTimeReferential;
    }

    public BigDecimal getPsFirstBracketPrice() {
	return psFirstBracketPrice;
    }

    public void setPsFirstBracketPrice(BigDecimal psFirstBracketPrice) {
	this.psFirstBracketPrice = psFirstBracketPrice;
    }

    public long getPsSecondBracketMinuteTimeReferential() {
	return psSecondBracketMinuteTimeReferential;
    }

    public void setPsSecondBracketMinuteTimeReferential(long psSecondBracketMinuteTimeReferential) {
	this.psSecondBracketMinuteTimeReferential = psSecondBracketMinuteTimeReferential;
    }

    public BigDecimal getPsSecondBracketPrice() {
	return psSecondBracketPrice;
    }

    public void setPsSecondBracketPrice(BigDecimal psSecondBracketPrice) {
	this.psSecondBracketPrice = psSecondBracketPrice;
    }

    public BigDecimal getPsMotorcycleCoefficient() {
	return psMotorcycleCoefficient;
    }

    public void setPsMotorcycleCoefficient(BigDecimal psMotorcycleCoefficient) {
	this.psMotorcycleCoefficient = psMotorcycleCoefficient;
    }

    public BigDecimal getPsLgpCoefficient() {
	return psLgpCoefficient;
    }

    public void setPsLgpCoefficient(BigDecimal psLgpCoefficient) {
	this.psLgpCoefficient = psLgpCoefficient;
    }

    public boolean isPsPrintIfNull() {
	return psPrintIfNull;
    }

    public void setPsPrintIfNull(boolean psPrintIfNull) {
	this.psPrintIfNull = psPrintIfNull;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}
