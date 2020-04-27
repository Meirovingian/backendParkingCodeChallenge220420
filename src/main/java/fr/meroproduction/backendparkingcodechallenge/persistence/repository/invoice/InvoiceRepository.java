package fr.meroproduction.backendparkingcodechallenge.persistence.repository.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("FROM Invoice i " //
	    + "INNER JOIN FETCH i.priceSheet AS ps " //
	    + "INNER JOIN FETCH ps.psFreeTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTimeRef " //
	    + "INNER JOIN FETCH ps.psSecondBracketTimeRef " //
	    + "INNER JOIN FETCH i.inOut AS io " //
	    + "INNER JOIN FETCH io.vehicle AS v " //
	    + "WHERE v.carRegistration = ?1")
    Invoice findInvoiceByCarRegistration(String carRegistration);

}
