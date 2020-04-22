package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetDTO;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;

@Service
public class PriceSheetService {

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    public PriceSheetDTO getLastActivatedPriceSheet() {
	Optional<PriceSheet> priceSheetInDatabase = priceSheetRepository.findById(2L);
	if (priceSheetInDatabase.isPresent()) {
	    return mapEntitytoDTO(priceSheetInDatabase.get());
	}
	return null;
    }

    private PriceSheetDTO mapEntitytoDTO(PriceSheet entity) {
	PriceSheetDTO display = new PriceSheetDTO();
	display.setPsFreeStartingMinuteTime(entity.getPsFreeStartingMinuteTime());
	display.setPsFirstBracketMinuteTime(entity.getPsFirstBracketMinuteTime());
	display.setPsFirstBracketMinuteTimeReferential(entity.getPsFirstBracketMinuteTimeReferential());
	display.setPsFirstBracketPrice(entity.getPsFirstBracketPrice());
	display.setPsSecondBracketMinuteTimeReferential(entity.getPsSecondBracketMinuteTimeReferential());
	display.setPsSecondBracketPrice(entity.getPsSecondBracketPrice());
	display.setPsMotorcycleCoefficient(entity.getPsMotorcycleCoefficient());
	display.setPsLgpCoefficient(entity.getPsLgpCoefficient());
	display.setPsPrintIfNull(entity.getPsPrintIfNull());
	return display;
    }

}
