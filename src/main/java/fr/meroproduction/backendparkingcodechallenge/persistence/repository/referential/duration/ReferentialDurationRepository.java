package fr.meroproduction.backendparkingcodechallenge.persistence.repository.referential.duration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.referential.duration.ReferentialDuration;

@Repository
public interface ReferentialDurationRepository extends JpaRepository<ReferentialDuration, Long> {

    @Query("FROM ReferentialDuration rd WHERE rd.activated = true AND rd.minuteDuration = ?1")
    ReferentialDuration findByDuration(Long duration);

}
