package fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet;

import java.io.Serializable;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.AbstractDTO;

public class PriceSheetSelectDTO extends AbstractDTO implements Serializable {

    private String description;

    private static final long serialVersionUID = -3164195605494230072L;

    public PriceSheetSelectDTO(long id) {
	super(id);
    }

    public PriceSheetSelectDTO(long id, String description) {
	this(id);
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

}
