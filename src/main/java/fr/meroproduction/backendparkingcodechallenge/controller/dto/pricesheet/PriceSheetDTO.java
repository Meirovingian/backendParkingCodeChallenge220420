package fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceSheetDTO implements Serializable {

    private long id;
    private long freeStartingTime;
    private BigDecimal firstBracketPrice;
    private long firstBracketReferentialDuration;
    private long firstBracketTime;
    private BigDecimal secondBracketPrice;
    private long secondBracketReferentialDuration;
    private BigDecimal motorcycleCoefficient;
    private BigDecimal lgpCoefficient;
    private boolean systematicPrint;

    private static final long serialVersionUID = -633201883590850051L;

    public PriceSheetDTO() {
	super();
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getFreeStartingTime() {
	return freeStartingTime;
    }

    public void setFreeStartingTime(long freeStartingTime) {
	this.freeStartingTime = freeStartingTime;
    }

    public BigDecimal getFirstBracketPrice() {
	return firstBracketPrice;
    }

    public void setFirstBracketPrice(BigDecimal firstBracketPrice) {
	this.firstBracketPrice = firstBracketPrice;
    }

    public long getFirstBracketReferentialDuration() {
	return firstBracketReferentialDuration;
    }

    public void setFirstBracketReferentialDuration(long firstBracketReferentialDuration) {
	this.firstBracketReferentialDuration = firstBracketReferentialDuration;
    }

    public long getFirstBracketTime() {
	return firstBracketTime;
    }

    public void setFirstBracketTime(long firstBracketTime) {
	this.firstBracketTime = firstBracketTime;
    }

    public BigDecimal getSecondBracketPrice() {
	return secondBracketPrice;
    }

    public void setSecondBracketPrice(BigDecimal secondBracketPrice) {
	this.secondBracketPrice = secondBracketPrice;
    }

    public long getSecondBracketReferentialDuration() {
	return secondBracketReferentialDuration;
    }

    public void setSecondBracketReferentialDuration(long secondBracketReferentialDuration) {
	this.secondBracketReferentialDuration = secondBracketReferentialDuration;
    }

    public BigDecimal getMotorcycleCoefficient() {
	return motorcycleCoefficient;
    }

    public void setMotorcycleCoefficient(BigDecimal motorcycleCoefficient) {
	this.motorcycleCoefficient = motorcycleCoefficient;
    }

    public BigDecimal getLgpCoefficient() {
	return lgpCoefficient;
    }

    public void setLgpCoefficient(BigDecimal lgpCoefficient) {
	this.lgpCoefficient = lgpCoefficient;
    }

    public boolean isSystematicPrint() {
	return systematicPrint;
    }

    public void setSystematicPrint(boolean systematicPrint) {
	this.systematicPrint = systematicPrint;
    }

}
