package fr.meroproduction.backendparkingcodechallenge.controller.inout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.inout.InOutDTO;
import fr.meroproduction.backendparkingcodechallenge.service.inout.InOutService;

@RestController
@RequestMapping(path = "/inout")
@CrossOrigin(origins = "${app.request.origin}")
public class InOutController {

    @Autowired
    private InOutService inOutService;

    @PutMapping(path = "/entry")
    public ResponseEntity<Boolean> entry(@RequestBody InOutDTO entrance) {
	return new ResponseEntity<>(inOutService.entry(entrance), HttpStatus.OK);
    }

}
