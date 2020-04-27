package fr.meroproduction.backendparkingcodechallenge.controller.temporary;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration.ReferentialDuration;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.referential.duration.ReferentialDurationRepository;

@RestController
@RequestMapping(path = "/temp")
@CrossOrigin(origins = "${app.request.origin}")
public class TemporaryController {

    @Autowired
    private ReferentialDurationRepository referentialDurationRepository;

    private static final Logger LOGGER = LogManager.getLogger(TemporaryController.class);

    @GetMapping(path = "/insertDurationReferential")
    public void insertDurationReferential() {
	List<ReferentialDuration> referentialDurationList = new ArrayList<>();
	referentialDurationList.add(new ReferentialDuration(30L, "30 minutes", "30 minutes", true));
	referentialDurationList.add(new ReferentialDuration(60L, "60 minutes", "1 heure", true));
	referentialDurationList.add(new ReferentialDuration(90L, "90 minutes", "1 heure et 30 minutes", true));
	referentialDurationList.add(new ReferentialDuration(120L, "120 minutes", "2 heures", true));
	referentialDurationList.add(new ReferentialDuration(150L, "150 minutes", "2 heures et 30 minutes", false));
	referentialDurationList.add(new ReferentialDuration(180L, "180 minutes", "3 heures", false));
	referentialDurationList.add(new ReferentialDuration(210L, "210 minutes", "3 heures et 30 minutes", false));
	referentialDurationList.add(new ReferentialDuration(240L, "240 minutes", "4 heures", false));
	referentialDurationList.add(new ReferentialDuration(270L, "270 minutes", "4 heures et 30 minutes", false));
	referentialDurationList.add(new ReferentialDuration(300L, "300 minutes", "5 heures", false));
	referentialDurationList.add(new ReferentialDuration(330L, "330 minutes", "5 heures et 30 minutes", false));
	referentialDurationList.add(new ReferentialDuration(360L, "360 minutes", "6 heures", false));
	referentialDurationRepository.saveAll(referentialDurationList);
	LOGGER.info("Duration referential has successfully been initialized !");
    }

}
