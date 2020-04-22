package fr.meroproduction.backendparkingcodechallenge.persistence.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
