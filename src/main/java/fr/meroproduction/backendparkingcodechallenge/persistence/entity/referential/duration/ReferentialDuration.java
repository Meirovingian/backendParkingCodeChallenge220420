package fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;

@Entity
@Table(name = "referential_duration")
public class ReferentialDuration extends AbstractJpaEntity {

    @Column(name = "minute_duration", nullable = false, precision = 5, scale = 0)
    private Long minuteDuration;

    @Column(name = "minute_description", nullable = false, length = 25)
    private String minuteDescription;

    @Column(name = "hour_description", nullable = false, length = 25)
    private String hourDescription;

    @Column(name = "allowed_free_time", nullable = false)
    private Boolean allowedOnFreeTime;

    public ReferentialDuration() {
	super();
    }

    public ReferentialDuration(Long minuteDuration, String minuteDescription, String hourDescription,
	    boolean allowedOnFreeTime) {
	super();
	this.minuteDuration = minuteDuration;
	this.minuteDescription = minuteDescription;
	this.hourDescription = hourDescription;
	this.allowedOnFreeTime = allowedOnFreeTime;
    }

    public Long getMinuteDuration() {
	return minuteDuration;
    }

    public void setMinuteDuration(Long minuteDuration) {
	this.minuteDuration = minuteDuration;
    }

    public String getMinuteDescription() {
	return minuteDescription;
    }

    public void setMinuteDescription(String minuteDescription) {
	this.minuteDescription = minuteDescription;
    }

    public String getHourDescription() {
	return hourDescription;
    }

    public void setHourDescription(String hourDescription) {
	this.hourDescription = hourDescription;
    }

    public Boolean getAllowedOnFreeTime() {
	return allowedOnFreeTime;
    }

    public void setAllowedOnFreeTime(Boolean allowedOnFreeTime) {
	this.allowedOnFreeTime = allowedOnFreeTime;
    }

}
