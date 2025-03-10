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
	
	public User addUser(User user) throws IllegalArgumentException {
        if (userRepository.findOneByUsername(user.getUsername()).isPresent()) 
        {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует.");
        }
        if (userRepository.findOneByEmail(user.getEmail()).isPresent()) 
        {
            throw new IllegalArgumentException("Пользователь с таким email уже существует.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
		return user;
    }

	public User updateUser(User user, User userUpdateDTO) {
        if (user != null) {
            if (userUpdateDTO.getUsername() != null && !"".equals(userUpdateDTO.getUsername())) {
                user.setUsername(userUpdateDTO.getUsername());
            }
            if (userUpdateDTO.getEmail() != null && !"".equals(userUpdateDTO.getEmail())) {
                user.setEmail(userUpdateDTO.getEmail());
            }
            if (userUpdateDTO.getPassword() != null && !"".equals(userUpdateDTO.getPassword())) {
                user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
            }
            if (userUpdateDTO.getPictureUrl() != null && !"".equals(userUpdateDTO.getPassword())) {
                user.setPictureUrl(userUpdateDTO.getPictureUrl());
            }
            return userRepository.save(user);
        }
        return null;
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
