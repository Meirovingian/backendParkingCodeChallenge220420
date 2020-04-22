package fr.meroproduction.backendparkingcodechallenge.persistence.repository.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.invoice.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
