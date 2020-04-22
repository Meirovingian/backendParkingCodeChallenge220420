package fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;

@Repository
public interface PriceSheetRepository extends JpaRepository<PriceSheet, Long> {

    @Query("FROM PriceSheet WHERE activated = true")
    PriceSheet findLastActivatedPriceSheet();

}
