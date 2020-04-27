package fr.meroproduction.backendparkingcodechallenge.controller.search.pricesheet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetSearchDTO;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.PriceSheetService;

@RestController
@RequestMapping(path = "/pricesheets")
@CrossOrigin(origins = "${app.request.origin}")
public class PriceSheetSearchController {

    @Autowired
    private PriceSheetService priceSheetService;

    @GetMapping("/search")
    public List<PriceSheetSearchDTO> getEntireList() {
	return priceSheetService.getEntireList();
    }
}
