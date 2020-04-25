package fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceSheetDTO implements Serializable {

    private long id;
    private String freeStartingTime;
    private String firstChargedPeriod;
    private String firstAppliedPrice;
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

    public String getFreeStartingTime() {
	return freeStartingTime;
    }

    public void setFreeStartingTime(String freeStartingTime) {
	this.freeStartingTime = freeStartingTime;
    }

    public String getFirstChargedPeriod() {
	return firstChargedPeriod;
    }

    public void setFirstChargedPeriod(String firstChargedPeriod) {
	this.firstChargedPeriod = firstChargedPeriod;
    }

    public String getFirstAppliedPrice() {
	return firstAppliedPrice;
    }

    public void setFirstAppliedPrice(String firstAppliedPrice) {
	this.firstAppliedPrice = firstAppliedPrice;
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
