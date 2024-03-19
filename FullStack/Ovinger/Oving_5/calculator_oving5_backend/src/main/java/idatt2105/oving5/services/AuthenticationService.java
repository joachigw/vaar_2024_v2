package idatt2105.oving5.services;

import idatt2105.oving5.model.Role;
import idatt2105.oving5.model.User;
import idatt2105.oving5.repository.RoleRepository;
import idatt2105.oving5.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User registerUser(String username, String password) {

        String encodedPassword = encoder.encode(password);
        Role userRole = null;
        if (roleRepository.findByAuthority("USER").isPresent()) userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new User(username, encodedPassword, authorities));
    }
}