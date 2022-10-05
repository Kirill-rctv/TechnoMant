package ru.kochnev.technomant.SpringBoot.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kochnev.technomant.SpringBoot.models.MyException;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.models.UserRole;
import ru.kochnev.technomant.SpringBoot.modelsDTO.UserDTO;
import ru.kochnev.technomant.SpringBoot.repositories.RoleRepository;
import ru.kochnev.technomant.SpringBoot.repositories.UserRepository;
import ru.kochnev.technomant.SpringBoot.repositories.UserRoleRepository;
import ru.kochnev.technomant.SpringBoot.validators.AuthorValidator;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorValidator authorValidator;

    public User findById(UUID id) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(id));
        return optionalUser.orElse(null);
    }

    public User findByName(String name) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByName(name));
        return optionalUser.orElse(null);
    }

    @Transactional
    public User save(User user) {
        if (findByName(user.getName()) == null) {
            userRepository.insert(user
                    .setId(UUID.randomUUID())
                    .setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
                    .setActive(1));

            userRoleRepository.insert(new UserRole()
                    .setRoleId(roleRepository.get("ROLE_WRITER").getId())
                    .setUserId(user.getId()));
        }
        return user;
    }

    public UserDTO getById(String id) throws MyException {
        User userFromDB = Optional.ofNullable(userRepository.findById(UUID.fromString(id)))
                .orElseThrow(() -> new MyException("User not found"));
        return modelMapper.map(userFromDB, UserDTO.class);
    }

    @Transactional
    public void update(User updatedUser) {
        if(authorValidator.authorValid(updatedUser.getId())) {
            updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            if (userRepository.update(updatedUser) == 0) {
                throw new MyException("Error updating user. User not found");
            }
        } else {
            throw new MyException("Access denied!");
        }
    }

    @Transactional
    public void deleteById(String id) {
        if(authorValidator.authorValid(UUID.fromString(id))) {
            if (userRepository.deleteById(UUID.fromString(id)) == 0) {
                throw new MyException("Error deleting user. User not found");
            }
        } else {
            throw new MyException("Access denied!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }
}