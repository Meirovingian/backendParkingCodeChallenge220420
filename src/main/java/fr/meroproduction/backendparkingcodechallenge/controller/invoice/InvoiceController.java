package fr.meroproduction.backendparkingcodechallenge.controller.invoice;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.InvoiceService;

@RestController
@RequestMapping(path = "/invoice")
@CrossOrigin(origins = "${app.request.origin}")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping(path = "/{outgoingDate}/pay/{carRegistration}")
    public BigDecimal pay(
	    @PathVariable(name = "outgoingDate") @DateTimeFormat(pattern = "ddMMyyHHmmss") Date outgoingDate,
	    @PathVariable String carRegistration) {
	return invoiceService.pay(outgoingDate, carRegistration);
    }

}
