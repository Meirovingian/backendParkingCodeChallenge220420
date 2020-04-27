package fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.user.ParkingUser;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;

@Entity
@Table(name = "in_out")
public class InOut extends AbstractJpaEntity {

    @Column(name = "incoming_date", nullable = false)
    private Date incomingDate;

    @Column(name = "outgoing_date")
    private Date outgoingDate;

    @Column(name = "revised_1")
    private Date revised1;

    @Column(name = "revised_2")
    private Date revised2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ParkingUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public InOut() {
	super();
    }

    public Date getIncomingDate() {
	return incomingDate;
    }

    public void setIncomingDate(Date incomingDate) {
	this.incomingDate = incomingDate;
    }

    public Date getOutgoingDate() {
	return outgoingDate;
    }

    public void setOutgoingDate(Date outgoingDate) {
	this.outgoingDate = outgoingDate;
    }

    public Date getRevised1() {
	return revised1;
    }

    public void setRevised1(Date revised1) {
	this.revised1 = revised1;
    }

    public Date getRevised2() {
	return revised2;
    }

    public void setRevised2(Date revised2) {
	this.revised2 = revised2;
    }

    public ParkingUser getUser() {
	return user;
    }

    public void setUser(ParkingUser user) {
	this.user = user;
    }

    public Vehicle getVehicle() {
	return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
    }

    @PrePersist
    private void onEntrance() {
	this.incomingDate = new Date();
    }
}
