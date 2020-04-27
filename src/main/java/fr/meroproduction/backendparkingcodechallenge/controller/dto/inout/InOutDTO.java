package fr.meroproduction.backendparkingcodechallenge.controller.dto.inout;

import java.io.Serializable;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.FuelType;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.VehicleType;

public class InOutDTO implements Serializable {

    private static final long serialVersionUID = -3132573747463646079L;

    private long priceSheetId;
    private String carRegistrationPartOne;
    private String carRegistrationPartTwo;
    private String carRegistrationPartThree;
    private FuelType fuelType;
    private VehicleType vehicleType;

    public InOutDTO() {
	super();
    }

    public long getPriceSheetId() {
	return priceSheetId;
    }

    public void setPriceSheetId(long priceSheetId) {
	this.priceSheetId = priceSheetId;
    }

    public String getCarRegistrationPartOne() {
	return carRegistrationPartOne;
    }

    public void setCarRegistrationPartOne(String carRegistrationPartOne) {
	this.carRegistrationPartOne = carRegistrationPartOne;
    }

    public String getCarRegistrationPartTwo() {
	return carRegistrationPartTwo;
    }

    public void setCarRegistrationPartTwo(String carRegistrationPartTwo) {
	this.carRegistrationPartTwo = carRegistrationPartTwo;
    }

    public String getCarRegistrationPartThree() {
	return carRegistrationPartThree;
    }

    public void setCarRegistrationPartThree(String carRegistrationPartThree) {
	this.carRegistrationPartThree = carRegistrationPartThree;
    }

    public FuelType getFuelType() {
	return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
	this.fuelType = fuelType;
    }

    public VehicleType getVehicleType() {
	return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
	this.vehicleType = vehicleType;
    }

}
