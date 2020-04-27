package fr.meroproduction.backendparkingcodechallenge.controller.dto;

public abstract class AbstractDTO {

    private long id;

    public AbstractDTO() {
	super();
    }

    public AbstractDTO(long id) {
	super();
	this.id = id;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}
