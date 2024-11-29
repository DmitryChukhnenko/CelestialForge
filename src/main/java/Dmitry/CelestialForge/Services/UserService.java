package Dmitry.CelestialForge.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

    public User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
	
	public User addUser(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User findById(Long id){
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) return user.get();
		return null;
	}
	
	public User findByEmail(String email){
		Optional<User> user = userRepository.findOneByEmail(email);
		if(user.isPresent()) return user.get();
		return null;
	}

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findOneByUsername(username);
		if(user.isPresent()) return user.get();
		return null;
    }
}
