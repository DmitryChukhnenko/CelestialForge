package Dmitry.CelestialForge.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Dmitry.CelestialForge.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUsername(String username);
}
