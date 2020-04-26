package fr.meroproduction.backendparkingcodechallenge.service.referential.duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.referential.duration.DurationRefDTO;
import fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration.ReferentialDuration;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.referential.duration.ReferentialDurationRepository;

@Service
public class ReferentialDurationService {

    @Autowired
    private ReferentialDurationRepository referentialDurationRepository;

    public List<DurationRefDTO> getEntireList() {
	List<ReferentialDuration> entityList = referentialDurationRepository.findAll();
	List<DurationRefDTO> durationRefList = new ArrayList<>();
	durationRefList.add(new DurationRefDTO(-1L, null, "", "", true));
	if (!CollectionUtils.isEmpty(entityList)) {
	    // TODO Créer un mapper
	    durationRefList
		    .addAll(entityList.stream().map(entity -> mapEntityToDTO(entity)).collect(Collectors.toList()));
	    return durationRefList;
	}
	return durationRefList;
    }

    public Map<Long, ReferentialDuration> findByIdentifierSet(final Set<Long> identifierSet) {
	return referentialDurationRepository.findAllById(identifierSet).stream()
		.collect(Collectors.toMap(ReferentialDuration::getId, Function.identity()));
    }

    private DurationRefDTO mapEntityToDTO(final ReferentialDuration entity) {
	DurationRefDTO durationRefDTO = new DurationRefDTO();
	durationRefDTO.setId(entity.getId());
	durationRefDTO.setMinuteDuration(entity.getMinuteDuration());
	durationRefDTO.setMinuteDescription(entity.getMinuteDescription());
	durationRefDTO.setHourDescription(entity.getHourDescription());
	durationRefDTO.setAllowedOnFreeTime(entity.getAllowedOnFreeTime());
	return durationRefDTO;
    }

}
