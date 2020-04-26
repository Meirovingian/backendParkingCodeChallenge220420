package fr.meroproduction.backendparkingcodechallenge.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.meroproduction.backendparkingcodechallenge.persistence.entity.user.ParkingUser;
import fr.meroproduction.backendparkingcodechallenge.persistence.repository.user.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ParkingUser saveUser(ParkingUser userToSave) {
	return userRepository.save(userToSave);
    }

}
