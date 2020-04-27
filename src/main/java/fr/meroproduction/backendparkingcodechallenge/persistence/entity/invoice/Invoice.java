package fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.AbstractJpaEntity;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;

@Entity
@Table(name = "invoice")
public class Invoice extends AbstractJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_sheet_id", referencedColumnName = "id", nullable = false)
    private PriceSheet priceSheet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_out_id", referencedColumnName = "id", nullable = false)
    private InOut inOut;

    @Column(name = "rounded_total", precision = 9, scale = 2)
    private BigDecimal roundedTotal;

    public Invoice() {
	super();
    }

    public PriceSheet getPriceSheet() {
	return priceSheet;
    }

    public void setPriceSheet(PriceSheet priceSheet) {
	this.priceSheet = priceSheet;
    }

    public InOut getInOut() {
	return inOut;
    }

    public void setInOut(InOut inOut) {
	this.inOut = inOut;
    }

    public BigDecimal getRoundedTotal() {
	return roundedTotal;
    }

    public void setRoundedTotal(BigDecimal roundedTotal) {
	this.roundedTotal = roundedTotal;
    }

}
