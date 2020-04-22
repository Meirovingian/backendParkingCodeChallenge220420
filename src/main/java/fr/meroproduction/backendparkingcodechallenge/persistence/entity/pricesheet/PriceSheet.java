package fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;

@Entity
@Table(name = "price_sheet")
public class PriceSheet extends AbstractJpaEntity {

    @Column(name = "ps_free_starting_minute_time", nullable = false, precision = 5, scale = 0)
    private Long psFreeStartingMinuteTime;

    @Column(name = "ps_first_bracket_minute_time", nullable = false, precision = 5, scale = 0)
    private Long psFirstBracketMinuteTime;

    @Column(name = "ps_first_bracket_minute_time_referential", nullable = false, precision = 5, scale = 0)
    private Long psFirstBracketMinuteTimeReferential;

    @Column(name = "ps_first_bracket_price", nullable = false, precision = 5, scale = 2)
    private BigDecimal psFirstBracketPrice;

    @Column(name = "ps_second_bracket_minute_time_referential", nullable = false, precision = 5, scale = 0)
    private Long psSecondBracketMinuteTimeReferential;

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

    public Long getPsFreeStartingMinuteTime() {
	return psFreeStartingMinuteTime;
    }

    public void setPsFreeStartingMinuteTime(Long psFreeStartingMinuteTime) {
	this.psFreeStartingMinuteTime = psFreeStartingMinuteTime;
    }

    public Long getPsFirstBracketMinuteTime() {
	return psFirstBracketMinuteTime;
    }

    public void setPsFirstBracketMinuteTime(Long psFirstBracketMinuteTime) {
	this.psFirstBracketMinuteTime = psFirstBracketMinuteTime;
    }

    public Long getPsFirstBracketMinuteTimeReferential() {
	return psFirstBracketMinuteTimeReferential;
    }

    public void setPsFirstBracketMinuteTimeReferential(Long psFirstBracketMinuteTimeReferential) {
	this.psFirstBracketMinuteTimeReferential = psFirstBracketMinuteTimeReferential;
    }

    public BigDecimal getPsFirstBracketPrice() {
	return psFirstBracketPrice;
    }

    public void setPsFirstBracketPrice(BigDecimal psFirstBracketPrice) {
	this.psFirstBracketPrice = psFirstBracketPrice;
    }

    public Long getPsSecondBracketMinuteTimeReferential() {
	return psSecondBracketMinuteTimeReferential;
    }

    public void setPsSecondBracketMinuteTimeReferential(Long psSecondBracketMinuteTimeReferential) {
	this.psSecondBracketMinuteTimeReferential = psSecondBracketMinuteTimeReferential;
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
