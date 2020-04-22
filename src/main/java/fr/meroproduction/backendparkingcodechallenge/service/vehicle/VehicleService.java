package fr.meroproduction.backendparkingcodechallenge.service.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.vehicle.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle getVehicleByCarRegistration(String carRegistration) {
	return vehicleRepository.findActiveByCarRegistration(carRegistration);
    }

    public Vehicle saveVehicle(Vehicle vehicleToSave) {
	return vehicleRepository.save(vehicleToSave);
    }

}
