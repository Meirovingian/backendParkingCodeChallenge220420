package fr.meroproduction.backendparkingcodechallenge.controller.inout;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.service.inout.InOutService;

@RestController
@RequestMapping(path = "/inout")
@CrossOrigin(origins = "${app.request.origin}")
public class InOutController {

    @Autowired
    private InOutService inOutService;

    @GetMapping(path = "/entry/{carRegistration}")
    public boolean entry(@PathVariable String carRegistration) {
	return inOutService.entry(carRegistration);
    }

    @GetMapping(path = "/{outgoingDate}/pay/{carRegistration}")
    public BigDecimal pay(
	    @PathVariable(name = "outgoingDate") @DateTimeFormat(pattern = "ddMMyyHHmmss") Date outgoingDate,
	    @PathVariable String carRegistration) {
	return inOutService.pay(outgoingDate, carRegistration);
    }
}
