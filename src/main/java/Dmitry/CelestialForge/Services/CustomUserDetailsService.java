// package Dmitry.CelestialForge.Services;

// import java.util.ArrayList;
// import java.util.Collection;

// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import Dmitry.CelestialForge.Entities.User;
// import Dmitry.CelestialForge.Repositories.UserRepository;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         User user = userRepository.findOneByEmail(email).get();
//         if(user == null){
//             throw new UsernameNotFoundException("Пользователь не найден с email: " + email);
//         }
//         LoggerFactory.getLogger(CustomUserDetailsService.class).info("Загружен пользователь: " + user.getEmail() + " " + user.getPassword());
//         return new org.springframework.security.core.userdetails.User(
//             user.getEmail(),
//             user.getPassword(),
//             getAuthorities(user) // Реализуйте метод для получения ролей, если есть
//         );
//     }

//     private Collection<? extends GrantedAuthority> getAuthorities(@SuppressWarnings("unused") User user) {
//         // Пример без ролей
//         return new ArrayList<>(){};
//     }
// }

