package fr.meroproduction.backendparkingcodechallenge.persistence.entity.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;

@Entity
@Table(name = "parking_user")
public class ParkingUser extends AbstractJpaEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "birth_date")
    private Date birtDate;

    @Column(name = "is_employee")
    private Boolean isEmployee;

    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicleList;

    @OneToMany(mappedBy = "user")
    private List<InOut> inOutList;

    public ParkingUser() {
	super();
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public Date getBirtDate() {
	return birtDate;
    }

    public void setBirtDate(Date birtDate) {
	this.birtDate = birtDate;
    }

    public Boolean getIsEmployee() {
	return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
	this.isEmployee = isEmployee;
    }

    public List<Vehicle> getVehicleList() {
	return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
	this.vehicleList = vehicleList;
    }

    public List<InOut> getInOutList() {
	return inOutList;
    }

    public void setInOutList(List<InOut> inOutList) {
	this.inOutList = inOutList;
    }

}
