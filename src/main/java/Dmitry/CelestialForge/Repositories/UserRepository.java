package Dmitry.CelestialForge.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Dmitry.CelestialForge.Entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUsername(String username);
}
