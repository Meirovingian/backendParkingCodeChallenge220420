package fr.meroproduction.backendparkingcodechallenge.controller.pricesheet;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetDTO;
import fr.meroproduction.backendparkingcodechallenge.controller.dto.pricesheet.PriceSheetSelectDTO;
import fr.meroproduction.backendparkingcodechallenge.service.pricesheet.PriceSheetService;

@RestController
@RequestMapping(path = "/pricesheets")
@CrossOrigin(origins = "${app.request.origin}")
public class PriceSheetController {

    @Autowired
    private PriceSheetService priceSheetService;

    @GetMapping("/ref")
    public List<PriceSheetSelectDTO> getPriceSheetSelectionList() {
	return priceSheetService.getPriceSheetSelectionList();
    }

    @GetMapping("/{id}")
    public PriceSheetDTO getSpecificPriceSheet(@PathVariable long id) {
	return priceSheetService.getPriceSheetToDisplay(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updatePriceSheet(@PathVariable long id,
	    @RequestBody PriceSheetDTO priceSheetToSave) {
	long updatedEntityId = priceSheetService.save(priceSheetToSave);
	return new ResponseEntity<>(updatedEntityId >= 0, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> createPriceSheet(@PathVariable long id,
	    @RequestBody PriceSheetDTO priceSheetToSave) {
	long createdEntityId = priceSheetService.save(priceSheetToSave);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEntityId)
		.toUri();
	return ResponseEntity.created(uri).build();
    }

}
