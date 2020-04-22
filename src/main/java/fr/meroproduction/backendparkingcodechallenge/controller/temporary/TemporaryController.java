package fr.meroproduction.backendparkingcodechallenge.controller.temporary;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;

@RestController
@RequestMapping(path = "${temporary.mapping.prefix}")
@CrossOrigin(origins = "${app.request.origin}")
public class TemporaryController {

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    @GetMapping(path = "/insertOnePriceSheet")
    public void insertOnePriceSheet() {
	PriceSheet ps = new PriceSheet();
	ps.setActivated(true);
	ps.setPsFreeStartingMinuteTime(60L);
	ps.setPsFirstBracketMinuteTime(240L);
	ps.setPsFirstBracketMinuteTimeReferential(60L);
	ps.setPsFirstBracketPrice(BigDecimal.valueOf(2d).setScale(2, RoundingMode.UP));
	ps.setPsSecondBracketMinuteTimeReferential(60L);
	ps.setPsSecondBracketPrice(BigDecimal.valueOf(1.5));
	ps.setPsMotorcycleCoefficient(BigDecimal.valueOf(0.5));
	ps.setPsLgpCoefficient(BigDecimal.valueOf(1.07));
	ps.setPsPrintIfNull(false);
	priceSheetRepository.save(ps);
    }
}
