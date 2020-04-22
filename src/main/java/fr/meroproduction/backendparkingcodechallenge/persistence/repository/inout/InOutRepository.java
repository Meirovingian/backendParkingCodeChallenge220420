package fr.meroproduction.backendparkingcodechallenge.persistence.repository.inout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.inout.InOut;

@Repository
public interface InOutRepository extends JpaRepository<InOut, Long> {

    @Query("FROM InOut io WHERE io.activated = true AND io.vehicle.carRegistration = ?1 and io.vehicle.activated = true")
    InOut findInOutRelatedToLastActivatedVehicleRegistration(String carRegistration);

}
