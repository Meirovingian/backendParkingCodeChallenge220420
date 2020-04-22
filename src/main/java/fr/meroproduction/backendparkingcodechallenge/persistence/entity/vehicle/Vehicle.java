package fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.user.ParkingUser;

@Entity
@Table(name = "vehicle")
public class Vehicle extends AbstractJpaEntity {

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "fuel_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "car_registration", nullable = false)
    private String carRegistration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ParkingUser user;

    public Vehicle() {
	super();
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

    public String getCarRegistration() {
	return carRegistration;
    }

    public void setCarRegistration(String carRegistration) {
	this.carRegistration = carRegistration;
    }

    public ParkingUser getUser() {
	return user;
    }

    public void setUser(ParkingUser user) {
	this.user = user;
    }

}
