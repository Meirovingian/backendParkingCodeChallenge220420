package fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration.ReferentialDuration;

@Entity
@Table(name = "price_sheet")
public class PriceSheet extends AbstractJpaEntity {

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_free_time_id", nullable = false)
    private ReferentialDuration psFreeTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_first_bracket_time_id", nullable = false)
    private ReferentialDuration psFirstBracketTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_first_bracket_time_ref_id", nullable = false)
    private ReferentialDuration psFirstBracketTimeRef;

    @Column(name = "ps_first_bracket_price", nullable = false, precision = 5, scale = 2)
    private BigDecimal psFirstBracketPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ps_second_bracket_time_ref_id", nullable = false)
    private ReferentialDuration psSecondBracketTimeRef;

    @Column(name = "ps_second_bracket_price", nullable = false, precision = 5, scale = 2)
    private BigDecimal psSecondBracketPrice;

    @Column(name = "ps_motorcycle_coefficient", nullable = false, precision = 4, scale = 2)
    private BigDecimal psMotorcycleCoefficient;

    @Column(name = "ps_lgp_coefficient", nullable = false, precision = 4, scale = 2)
    private BigDecimal psLgpCoefficient;

    @Column(name = "ps_print_if_null", nullable = false)
    private Boolean psPrintIfNull;

    public PriceSheet() {
	super();
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public ReferentialDuration getPsFreeTime() {
	return psFreeTime;
    }

    public void setPsFreeTime(ReferentialDuration psFreeTime) {
	this.psFreeTime = psFreeTime;
    }

    public ReferentialDuration getPsFirstBracketTime() {
	return psFirstBracketTime;
    }

    public void setPsFirstBracketTime(ReferentialDuration psFirstBracketTime) {
	this.psFirstBracketTime = psFirstBracketTime;
    }

    public ReferentialDuration getPsFirstBracketTimeRef() {
	return psFirstBracketTimeRef;
    }

    public void setPsFirstBracketTimeRef(ReferentialDuration psFirstBracketTimeRef) {
	this.psFirstBracketTimeRef = psFirstBracketTimeRef;
    }

    public BigDecimal getPsFirstBracketPrice() {
	return psFirstBracketPrice;
    }

    public void setPsFirstBracketPrice(BigDecimal psFirstBracketPrice) {
	this.psFirstBracketPrice = psFirstBracketPrice;
    }

    public ReferentialDuration getPsSecondBracketTimeRef() {
	return psSecondBracketTimeRef;
    }

    public void setPsSecondBracketTimeRef(ReferentialDuration psSecondBracketTimeRef) {
	this.psSecondBracketTimeRef = psSecondBracketTimeRef;
    }

    public BigDecimal getPsSecondBracketPrice() {
	return psSecondBracketPrice;
    }

    public void setPsSecondBracketPrice(BigDecimal psSecondBracketPrice) {
	this.psSecondBracketPrice = psSecondBracketPrice;
    }

    public BigDecimal getPsMotorcycleCoefficient() {
	return psMotorcycleCoefficient;
    }

    public void setPsMotorcycleCoefficient(BigDecimal psMotorcycleCoefficient) {
	this.psMotorcycleCoefficient = psMotorcycleCoefficient;
    }

    public BigDecimal getPsLgpCoefficient() {
	return psLgpCoefficient;
    }

    public void setPsLgpCoefficient(BigDecimal psLgpCoefficient) {
	this.psLgpCoefficient = psLgpCoefficient;
    }

    public Boolean getPsPrintIfNull() {
	return psPrintIfNull;
    }

    public void setPsPrintIfNull(Boolean psPrintIfNull) {
	this.psPrintIfNull = psPrintIfNull;
    }

}
