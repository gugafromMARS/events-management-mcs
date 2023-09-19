package gsc.projects.usersmcs.service;

import gsc.projects.usersmcs.converter.UserConverter;
import gsc.projects.usersmcs.dto.UserCreateDto;
import gsc.projects.usersmcs.dto.UserDto;
import gsc.projects.usersmcs.model.User;
import gsc.projects.usersmcs.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    private UserConverter userConverter;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userConverter.toDto(user))
                .toList();
    }

    public UserDto getById(Long userId) {
        return userRepository.findById(userId).stream()
                .map(user -> userConverter.toDto(user))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserDto create(UserCreateDto userCreateDto){
        User existingUser = userRepository.findByEmail(userCreateDto.getEmail());
        if(existingUser != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        User newUser = userConverter.fromCreateDto(userCreateDto);
        userRepository.save(newUser);
        return userConverter.toDto(newUser);
    }


}
