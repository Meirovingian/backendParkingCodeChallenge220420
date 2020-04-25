package fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet;

import java.io.Serializable;

public class PriceSheetSearchDTO implements Serializable {

    private long id;
    private String freeStartingTime;
    private String firstBracketPrice;
    private String firstBracketReferentialDuration;
    private String firstBracketTime;
    private String secondBracketPrice;
    private String secondBracketReferentialDuration;
    private String motorcycleCoefficient;
    private String lgpCoefficient;
    private boolean systematicPrint;

    private static final long serialVersionUID = -2618110490770391854L;

    public PriceSheetSearchDTO() {
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

    public String getFirstBracketPrice() {
	return firstBracketPrice;
    }

    public void setFirstBracketPrice(String firstBracketPrice) {
	this.firstBracketPrice = firstBracketPrice;
    }

    public String getFirstBracketReferentialDuration() {
	return firstBracketReferentialDuration;
    }

    public void setFirstBracketReferentialDuration(String firstBracketReferentialDuration) {
	this.firstBracketReferentialDuration = firstBracketReferentialDuration;
    }

    public String getFirstBracketTime() {
	return firstBracketTime;
    }

    public void setFirstBracketTime(String firstBracketTime) {
	this.firstBracketTime = firstBracketTime;
    }

    public String getSecondBracketPrice() {
	return secondBracketPrice;
    }

    public void setSecondBracketPrice(String secondBracketPrice) {
	this.secondBracketPrice = secondBracketPrice;
    }

    public String getSecondBracketReferentialDuration() {
	return secondBracketReferentialDuration;
    }

    public void setSecondBracketReferentialDuration(String secondBracketReferentialDuration) {
	this.secondBracketReferentialDuration = secondBracketReferentialDuration;
    }

    public String getMotorcycleCoefficient() {
	return motorcycleCoefficient;
    }

    public void setMotorcycleCoefficient(String motorcycleCoefficient) {
	this.motorcycleCoefficient = motorcycleCoefficient;
    }

    public String getLgpCoefficient() {
	return lgpCoefficient;
    }

    public void setLgpCoefficient(String lgpCoefficient) {
	this.lgpCoefficient = lgpCoefficient;
    }

    public boolean isSystematicPrint() {
	return systematicPrint;
    }

    public void setSystematicPrint(boolean systematicPrint) {
	this.systematicPrint = systematicPrint;
    }

}
