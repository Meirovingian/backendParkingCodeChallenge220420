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
    private static final String MINUS_SYMBOL = "-";
    private static final String PERCENT_SYMBOL = "%";
    private static final String UNDEFINED_PERCENT_VALUE = "undefined";

    private PriceSheet getNewInstance() {
	return new PriceSheet();
    }

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
	PriceSheetDTO priceSheetDTO = new PriceSheetDTO();
	priceSheetDTO.setId(entity.getId());
	priceSheetDTO.setFreeStartingTime(entity.getPsFreeTime().getId());
	priceSheetDTO.setFirstBracketPrice(entity.getPsFirstBracketPrice());
	priceSheetDTO.setFirstBracketReferentialDuration(entity.getPsFirstBracketTimeRef().getId());
	priceSheetDTO.setFirstBracketTime(entity.getPsFirstBracketTime().getId());
	priceSheetDTO.setSecondBracketPrice(entity.getPsSecondBracketPrice());
	priceSheetDTO.setSecondBracketReferentialDuration(entity.getPsSecondBracketTimeRef().getId());
	priceSheetDTO.setMotorcycleCoefficient(entity.getPsMotorcycleCoefficient());
	priceSheetDTO.setLgpCoefficient(entity.getPsLgpCoefficient());
	priceSheetDTO.setSystematicPrint(entity.getPsPrintIfNull());
	return priceSheetDTO;
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
	entity.setPsFreeTime(referentialDurationMap.get(dto.getFreeStartingTime()));
	entity.setPsFirstBracketTime(referentialDurationMap.get(dto.getFirstBracketTime()));
	entity.setPsFirstBracketTimeRef(referentialDurationMap.get(dto.getFirstBracketReferentialDuration()));
	entity.setPsFirstBracketPrice(dto.getFirstBracketPrice());
	entity.setPsSecondBracketTimeRef(referentialDurationMap.get(dto.getSecondBracketReferentialDuration()));
	entity.setPsSecondBracketPrice(dto.getSecondBracketPrice());
	entity.setPsMotorcycleCoefficient(makeCoefficient(dto.getMotorcycleCoefficient()));
	entity.setPsLgpCoefficient(makeCoefficient(dto.getLgpCoefficient()));
	entity.setPsPrintIfNull(dto.isSystematicPrint());
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

    private String displayPercent(final BigDecimal coefficient) {
	StringBuilder stringBuilder = new StringBuilder();
	if (coefficient != null) {
	    BigDecimal formatted = coefficient;
	    if (coefficient.compareTo(BigDecimal.ONE) >= 0) {
		formatted = formatted.subtract(BigDecimal.ONE);
		stringBuilder.append(PLUS_SYMBOL);

	    } else {
		stringBuilder.append(MINUS_SYMBOL);
	    }
	    formatted = formatted.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(0);
	    stringBuilder.append(formatted);
	    stringBuilder.append(PERCENT_SYMBOL);
	    return stringBuilder.toString();
	}
	return UNDEFINED_PERCENT_VALUE;
    }

}
