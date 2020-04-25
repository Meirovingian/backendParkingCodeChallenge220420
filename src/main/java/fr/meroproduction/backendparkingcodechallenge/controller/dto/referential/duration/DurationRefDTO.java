package fr.meroproduction.backendparkingcodechallenge.controller.dto.referential.duration;

import java.io.Serializable;

public class DurationRefDTO implements Serializable {

    private long id;
    private Long minuteDuration;
    private String minuteDescription;
    private String hourDescription;
    private boolean allowedOnFreeTime;

    private static final long serialVersionUID = 1467582259183581907L;

    public DurationRefDTO() {
	super();
    }

    public DurationRefDTO(long id, Long minuteDuration, String minuteDescription, String hourDescription,
	    boolean allowedOnFreeTime) {
	super();
	this.id = id;
	this.minuteDuration = minuteDuration;
	this.minuteDescription = minuteDescription;
	this.hourDescription = hourDescription;
	this.allowedOnFreeTime = allowedOnFreeTime;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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

    public boolean isAllowedOnFreeTime() {
	return allowedOnFreeTime;
    }

    public void setAllowedOnFreeTime(boolean allowedOnFreeTime) {
	this.allowedOnFreeTime = allowedOnFreeTime;
    }

}
