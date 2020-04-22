package fr.meroproduction.backendparkingcodechallenge.persistence.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.vehicle.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Vehicle findByCarRegistration(final String carRegistration);

    @Query("FROM Vehicle WHERE carRegistration = ?1 and activated = true")
    Vehicle findActiveByCarRegistration(final String carRegistration);
}
