package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetDTO;
import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetSearchDTO;
import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetSelectDTO;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.pricesheet.PriceSheet;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration.ReferentialDuration;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;
import fr.meroproduction.backendparkingcodechallenge.service.referential.duration.ReferentialDurationService;

@Service
public class PriceSheetService {

    @Autowired
    private ReferentialDurationService referentialDurationService;

    @Autowired
    private PriceSheetRepository priceSheetRepository;

    private static final String PLUS_SYMBOL = "+";
    private static final String PERCENT_SYMBOL = "%";
    private static final String UNDEFINED_PERCENT_VALUE = "undefined";
    private static final String SPACE_SLASH_SYMBOL = " / ";
    private static final String EURO_SYMBOL = "€ ";

    private PriceSheet getNewInstance() {
	return new PriceSheet();
    }

    public PriceSheet getSpecificEntity(final long identifier) {
	Optional<PriceSheet> entity = priceSheetRepository.findById(identifier);
	return entity.isPresent() ? entity.get() : null;
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

    public PriceSheetDTO getPriceSheetToDisplay(long id) {
	Optional<PriceSheet> entity = priceSheetRepository.findById(id);
	return entity.isPresent() ? mapEntityToDTO(entity.get()) : null;
    }

    public long save(PriceSheetDTO dto) {
	Assert.notNull(dto, "Data transfer object mustn't be null in order to populate entity");
	long identifier = dto.getId();
	Optional<PriceSheet> optional = identifier >= 0 ? priceSheetRepository.findById(identifier) : Optional.empty();
	PriceSheet entity = optional.isPresent() ? optional.get() : getNewInstance();
	populateEntityWithDTO(entity, dto);
	return priceSheetRepository.save(entity).getId();
    }

    private PriceSheetSearchDTO populate(final PriceSheet entity) {
	PriceSheetSearchDTO priceSheetSearchDTO = new PriceSheetSearchDTO();
	priceSheetSearchDTO.setId(entity.getId());
	priceSheetSearchDTO.setFreeStartingTime(entity.getPsFreeTime().getHourDescription());
	priceSheetSearchDTO.setFirstBracketTime(entity.getPsFirstBracketTime().getHourDescription());
	priceSheetSearchDTO.setFirstBracketPrice(String.valueOf(entity.getPsFirstBracketPrice()));
	priceSheetSearchDTO.setMotorcycleCoefficient(displayPercent(entity.getPsMotorcycleCoefficient()));
	priceSheetSearchDTO.setLgpCoefficient(displayPercent(entity.getPsLgpCoefficient()));
	priceSheetSearchDTO.setSystematicPrint(entity.getPsPrintIfNull());
	return priceSheetSearchDTO;
    }

    private PriceSheetDTO mapEntityToDTO(final PriceSheet entity) {
	PriceSheetDTO priceSheetDTO = new PriceSheetDTO(entity.getId());
	priceSheetDTO.setFreeStartingTime(entity.getPsFreeTime().getId());
	priceSheetDTO.setFirstBracketPrice(entity.getPsFirstBracketPrice());
	priceSheetDTO.setFirstBracketReferentialDuration(entity.getPsFirstBracketTimeRef().getId());
	priceSheetDTO.setFirstBracketTime(entity.getPsFirstBracketTime().getId());
	priceSheetDTO.setSecondBracketPrice(entity.getPsSecondBracketPrice());
	priceSheetDTO.setSecondBracketReferentialDuration(entity.getPsSecondBracketTimeRef().getId());
	priceSheetDTO.setMotorcycleCoefficient(getPercentValue(entity.getPsMotorcycleCoefficient()));
	priceSheetDTO.setLgpCoefficient(getPercentValue(entity.getPsLgpCoefficient()));
	priceSheetDTO.setSystematicPrint(entity.getPsPrintIfNull());
	return priceSheetDTO;
    }

    public BigDecimal getPercentValue(BigDecimal coefficientValue) {
	if (coefficientValue != null) {
	    BigDecimal converted = coefficientValue;
	    if (coefficientValue.compareTo(BigDecimal.ONE) >= 0) {
		converted = converted.subtract(BigDecimal.ONE);
		converted = converted.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(0);

	    } else {
		converted = BigDecimal.TEN.multiply(BigDecimal.TEN)
			.subtract(converted.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(0));
		converted = converted.multiply(new BigDecimal(-1L));
	    }

	    return converted;
	}
	return BigDecimal.ZERO;
    }

    private void populateEntityWithDTO(final PriceSheet entity, final PriceSheetDTO dto) {
	Assert.notNull(entity, "Entity mustn't be null in order to update it");
	Assert.notNull(dto, "Data transfer object mustn't be null in order to populate entity");
	Set<Long> identifierSet = new HashSet<>();
	identifierSet.add(dto.getFreeStartingTime());
	identifierSet.add(dto.getFirstBracketReferentialDuration());
	identifierSet.add(dto.getFirstBracketTime());
	identifierSet.add(dto.getSecondBracketReferentialDuration());
	final Map<Long, ReferentialDuration> referentialDurationMap = referentialDurationService
		.findByIdentifierSet(identifierSet);
	ReferentialDuration psFreeTime = referentialDurationMap.get(dto.getFreeStartingTime());
	entity.setPsFreeTime(psFreeTime);
	ReferentialDuration psFirstBracketTime = referentialDurationMap.get(dto.getFirstBracketTime());
	entity.setPsFirstBracketTime(psFirstBracketTime);
	ReferentialDuration psFirstBracketTimeRef = referentialDurationMap
		.get(dto.getFirstBracketReferentialDuration());
	entity.setPsFirstBracketTimeRef(psFirstBracketTimeRef);
	BigDecimal firstBracketPrice = dto.getFirstBracketPrice();
	entity.setPsFirstBracketPrice(firstBracketPrice);
	ReferentialDuration psSecondBracketTimeRef = referentialDurationMap
		.get(dto.getSecondBracketReferentialDuration());
	entity.setPsSecondBracketTimeRef(psSecondBracketTimeRef);
	BigDecimal secondBracketPrice = dto.getSecondBracketPrice();
	entity.setPsSecondBracketPrice(secondBracketPrice);
	BigDecimal motorcycleCoefficient = makeCoefficient(dto.getMotorcycleCoefficient());
	entity.setPsMotorcycleCoefficient(motorcycleCoefficient);
	BigDecimal lgpCoefficient = makeCoefficient(dto.getLgpCoefficient());
	entity.setPsLgpCoefficient(lgpCoefficient);
	entity.setPsPrintIfNull(dto.isSystematicPrint());
	StringBuilder description = new StringBuilder();
	description.append("Gratuit pour ");
	description.append(psFreeTime.getHourDescription());
	description.append(SPACE_SLASH_SYMBOL);
	description.append(firstBracketPrice);
	description.append(EURO_SYMBOL);
	description.append(" chaque ");
	description.append(psFirstBracketTimeRef.getHourDescription());
	description.append(" pendant ");
	description.append(psFirstBracketTime.getHourDescription());
	description.append(SPACE_SLASH_SYMBOL);
	description.append(secondBracketPrice);
	description.append(EURO_SYMBOL);
	description.append(" chaque ");
	description.append(psSecondBracketTimeRef.getHourDescription());
	description.append(SPACE_SLASH_SYMBOL);
	description.append("Moto. coeff. : ");
	description.append(motorcycleCoefficient);
	description.append(SPACE_SLASH_SYMBOL);
	description.append("GPL coeff. : ");
	description.append(lgpCoefficient);
	entity.setDescription(description.toString());
    }

    public BigDecimal makeCoefficient(final BigDecimal percentValue) {
	if (percentValue != null) {
	    BigDecimal formatted = null;
	    if (percentValue.compareTo(BigDecimal.ZERO) >= 0) {
		formatted = BigDecimal.ONE.add(percentValue.divide(BigDecimal.TEN).divide(BigDecimal.TEN)).setScale(2);

	    } else {
		BigDecimal temp = BigDecimal.TEN.multiply(BigDecimal.TEN).add(percentValue).divide(BigDecimal.TEN)
			.divide(BigDecimal.TEN).setScale(2);
		formatted = BigDecimal.ZERO.add(temp);
	    }
	    return formatted;
	}
	return BigDecimal.ZERO;
    }

    private String displayPercent(final BigDecimal coefficientValue) {
	if (coefficientValue != null) {
	    StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append(coefficientValue.compareTo(BigDecimal.ONE) >= 0 ? PLUS_SYMBOL : "");
	    stringBuilder.append(getPercentValue(coefficientValue));
	    stringBuilder.append(PERCENT_SYMBOL);
	    return stringBuilder.toString();
	}
	return UNDEFINED_PERCENT_VALUE;
    }

    public List<PriceSheetSelectDTO> getPriceSheetSelectionList() {
	List<PriceSheet> entityList = priceSheetRepository.findAll();
	List<PriceSheetSelectDTO> selectionList = new ArrayList<>();
	selectionList.add(new PriceSheetSelectDTO(-1L, ""));
	if (!CollectionUtils.isEmpty(entityList)) {
	    selectionList.addAll(entityList.stream().map(new Function<PriceSheet, PriceSheetSelectDTO>() {

		@Override
		public PriceSheetSelectDTO apply(PriceSheet entity) {
		    return new PriceSheetSelectDTO(entity.getId(), entity.getDescription());
		}

	    }).collect(Collectors.toList()));
	    return selectionList;
	}
	return selectionList;
    }

}
