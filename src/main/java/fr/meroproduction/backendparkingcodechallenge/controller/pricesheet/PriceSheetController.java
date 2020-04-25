package fr.meroproduction.backendparkingcodechallenge.controller.pricesheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetDTO;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.PriceSheetService;

@RestController
@RequestMapping(path = "/pricesheets")
@CrossOrigin(origins = "${app.request.origin}")
public class PriceSheetController {

    @Autowired
    private PriceSheetService priceSheetService;

    @GetMapping("/{id}")
    public PriceSheetDTO getSpecificTodo(@PathVariable long id) {
	return priceSheetService.getSpecificEntity(id);
    }

}
