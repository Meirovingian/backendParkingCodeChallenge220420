package fr.meroproduction.backendparkingcodechallenge.controller.referential;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.referential.duration.DurationRefDTO;
import fr.meroproduction.backendparkingcodechallenge.service.referential.duration.ReferentialDurationService;

@RestController
@RequestMapping(path = "/referential")
@CrossOrigin(origins = "${app.request.origin}")
public class ReferentialController {

    @Autowired
    private ReferentialDurationService referentialDurationService;

    @GetMapping
    public List<DurationRefDTO> getEntireList() {
	return referentialDurationService.getEntireList();
    }

}
