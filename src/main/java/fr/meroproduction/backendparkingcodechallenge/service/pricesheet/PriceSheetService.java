package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetDTO;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;

@Service
public class PriceSheetService {

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    public PriceSheet getLastActivatedPriceSheet() {
	return priceSheetRepository.findLastActivatedPriceSheet();
    }

    public List<PriceSheetDTO> getEntireList() {
	List<PriceSheet> entityList = priceSheetRepository.findAll();
	List<PriceSheetDTO> priceSheetList = new ArrayList<>();
	if (!CollectionUtils.isEmpty(entityList)) {
	    return entityList.stream().map(new Function<PriceSheet, PriceSheetDTO>() {

		@Override
		public PriceSheetDTO apply(PriceSheet entity) {
		    return mapEntityToDTO(entity);
		}

	    }).collect(Collectors.toList());
	}
	return priceSheetList;
    }

    private PriceSheetDTO mapEntityToDTO(final PriceSheet entity) {
	PriceSheetDTO priceSheetDTO = new PriceSheetDTO();
	priceSheetDTO.setId(entity.getId());
	priceSheetDTO.setFreeStartingTime(String.valueOf(entity.getPsFreeStartingMinuteTime()));
	priceSheetDTO.setFirstChargedPeriod(String.valueOf(entity.getPsFirstBracketMinuteTime()));
	priceSheetDTO.setFirstAppliedPrice(String.valueOf(entity.getPsFirstBracketPrice()));
	priceSheetDTO.setMotorcycleCoefficient(entity.getPsMotorcycleCoefficient());
	priceSheetDTO.setLgpCoefficient(entity.getPsLgpCoefficient());
	priceSheetDTO.setSystematicPrint(entity.getPsPrintIfNull());
	return priceSheetDTO;
    }
}
