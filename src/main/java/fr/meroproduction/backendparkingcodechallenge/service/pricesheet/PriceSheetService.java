package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetDTO;
import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetSearchDTO;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;
import fr.meroproduction.backendparkingcodechallenge.service.referential.duration.ReferentialDurationService;

@Service
public class PriceSheetService {

    @Autowired
    private ReferentialDurationService referentialDurationService;

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    public PriceSheet getLastActivatedPriceSheet() {
	return priceSheetRepository.findLastActivatedPriceSheet();
    }

    public List<PriceSheetSearchDTO> getEntireList() {
	List<PriceSheet> entityList = priceSheetRepository.findAll();
	List<PriceSheetSearchDTO> priceSheetList = new ArrayList<>();
	if (!CollectionUtils.isEmpty(entityList)) {
	    return entityList.stream().map(new Function<PriceSheet, PriceSheetSearchDTO>() {

		@Override
		public PriceSheetSearchDTO apply(PriceSheet entity) {
		    return populate(entity);
		}

	    }).collect(Collectors.toList());
	}
	return priceSheetList;
    }

    public PriceSheetDTO getSpecificEntity(long id) {
	Optional<PriceSheet> entity = priceSheetRepository.findById(id);
	return entity.isPresent() ? mapEntityToDTO(entity.get()) : null;
    }

    private PriceSheetSearchDTO populate(final PriceSheet entity) {
	PriceSheetSearchDTO priceSheetSearchDTO = new PriceSheetSearchDTO();
	priceSheetSearchDTO.setId(entity.getId());
	priceSheetSearchDTO.setFreeStartingTime(referentialDurationService
		.getSpecificDuration(entity.getPsFreeStartingMinuteTime()).getHourDescription());
	priceSheetSearchDTO.setFirstBracketTime(referentialDurationService
		.getSpecificDuration(entity.getPsFirstBracketMinuteTime()).getHourDescription());
	priceSheetSearchDTO.setFirstBracketPrice(String.valueOf(entity.getPsFirstBracketPrice()));
	priceSheetSearchDTO.setMotorcycleCoefficient(displayPercent(entity.getPsMotorcycleCoefficient()));
	priceSheetSearchDTO.setLgpCoefficient(displayPercent(entity.getPsLgpCoefficient()));
	priceSheetSearchDTO.setSystematicPrint(entity.getPsPrintIfNull());
	return priceSheetSearchDTO;
    }

    private String displayPercent(BigDecimal coefficient) {
	StringBuilder stringBuilder = new StringBuilder();
	if (coefficient != null) {
	    BigDecimal formatted = coefficient;
	    if (coefficient.compareTo(BigDecimal.ONE) >= 0) {
		formatted = formatted.subtract(BigDecimal.ONE);
		stringBuilder.append("+");

	    } else {
		stringBuilder.append("-");
	    }
	    formatted = formatted.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(0);
	    stringBuilder.append(formatted);
	    stringBuilder.append("%");
	    return stringBuilder.toString();
	}
	return "undefined";
    }

    private PriceSheetDTO mapEntityToDTO(final PriceSheet entity) {
	PriceSheetDTO priceSheetDTO = new PriceSheetDTO();
	priceSheetDTO.setId(entity.getId());
	priceSheetDTO.setFreeStartingTime(
		referentialDurationService.getSpecificDuration(entity.getPsFreeStartingMinuteTime()).getId());
	priceSheetDTO.setFirstBracketPrice(entity.getPsFirstBracketPrice());
	priceSheetDTO.setFirstBracketReferentialDuration(referentialDurationService
		.getSpecificDuration(entity.getPsFirstBracketMinuteTimeReferential()).getId());
	priceSheetDTO.setFirstBracketTime(
		referentialDurationService.getSpecificDuration(entity.getPsFirstBracketMinuteTime()).getId());
	priceSheetDTO.setSecondBracketPrice(entity.getPsSecondBracketPrice());
	priceSheetDTO.setSecondBracketReferentialDuration(referentialDurationService
		.getSpecificDuration(entity.getPsSecondBracketMinuteTimeReferential()).getId());
	priceSheetDTO.setMotorcycleCoefficient(entity.getPsMotorcycleCoefficient());
	priceSheetDTO.setLgpCoefficient(entity.getPsLgpCoefficient());
	priceSheetDTO.setSystematicPrint(entity.getPsPrintIfNull());
	return priceSheetDTO;
    }

}
