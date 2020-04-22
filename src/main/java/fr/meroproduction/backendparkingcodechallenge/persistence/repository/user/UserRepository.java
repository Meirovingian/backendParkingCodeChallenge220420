package fr.meroproduction.backendparkingcodechallenge.persistence.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.user.ParkingUser;

@Repository
public interface UserRepository extends JpaRepository<ParkingUser, Long> {

}
