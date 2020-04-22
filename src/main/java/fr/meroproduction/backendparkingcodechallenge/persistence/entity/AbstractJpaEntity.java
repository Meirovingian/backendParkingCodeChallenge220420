package fr.meroproduction.backendparkingcodechallenge.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private Date createdOn;

    private Date updatedOn;

    private Boolean activated;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getVersion() {
	return version;
    }

    public Date getCreatedOn() {
	return createdOn;
    }

    public Date getUpdatedOn() {
	return updatedOn;
    }

    public Boolean getActivated() {
	return activated;
    }

    public void setActivated(Boolean activated) {
	this.activated = activated;
    }

    @PrePersist
    private void onPersist() {
	this.createdOn = new Date();
    }

    @PreUpdate
    private void onUpdate() {
	this.updatedOn = new Date();
    }

}