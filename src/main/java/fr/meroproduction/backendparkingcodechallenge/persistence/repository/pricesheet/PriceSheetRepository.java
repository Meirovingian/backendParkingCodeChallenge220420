package fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;

@Repository
public interface PriceSheetRepository extends JpaRepository<PriceSheet, Long> {

    @Query("FROM PriceSheet ps " //
	    + "INNER JOIN FETCH ps.psFreeTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTimeRef " //
	    + "INNER JOIN FETCH ps.psSecondBracketTimeRef " //
	    + "WHERE ps.activated = true")
    PriceSheet findLastActivatedPriceSheet();

    @Override
    @Query("FROM PriceSheet ps " //
	    + "INNER JOIN FETCH ps.psFreeTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTimeRef " //
	    + "INNER JOIN FETCH ps.psSecondBracketTimeRef " //
	    + "WHERE ps.id = ?1")
    Optional<PriceSheet> findById(Long id);

    @Override
    @Query("FROM PriceSheet ps " //
	    + "INNER JOIN FETCH ps.psFreeTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTime " //
	    + "INNER JOIN FETCH ps.psFirstBracketTimeRef " //
	    + "INNER JOIN FETCH ps.psSecondBracketTimeRef ")
    List<PriceSheet> findAll();

}
