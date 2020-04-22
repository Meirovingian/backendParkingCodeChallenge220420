package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;

@Service
public class PriceSheetService {

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    public PriceSheet getLastActivatedPriceSheet() {
	return priceSheetRepository.findLastActivatedPriceSheet();
    }

}
