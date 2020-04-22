package fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;

@Entity
@Table(name = "vehicle")
public class Vehicle extends AbstractJpaEntity {

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "fuel_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    public Vehicle() {
	this(null, null);
    }

    public Vehicle(VehicleType vehicleType, FuelType fuelType) {
	this.vehicleType = vehicleType;
	this.fuelType = fuelType;
    }

    public VehicleType getVehicleType() {
	return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
	this.vehicleType = vehicleType;
    }

    public FuelType getFuelType() {
	return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
	this.fuelType = fuelType;
    }

}
