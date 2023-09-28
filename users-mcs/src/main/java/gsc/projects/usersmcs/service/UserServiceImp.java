package gsc.projects.usersmcs.service;

import gsc.projects.usersmcs.converter.UserConverter;
import gsc.projects.usersmcs.dto.*;
import gsc.projects.usersmcs.model.User;
import gsc.projects.usersmcs.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    private UserConverter userConverter;

    private APIClient apiClient;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userConverter.toDto(user))
                .toList();
    }

    @Override
    public UserDto getById(Long userId) {
        return userRepository.findById(userId).stream()
                .map(user -> userConverter.toDto(user))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto){
        User existingUser = userRepository.findByEmail(userCreateDto.getEmail());
        if(existingUser != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        User newUser = userConverter.fromCreateDto(userCreateDto);
        userRepository.save(newUser);
        return userConverter.toDto(newUser);
    }


    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultUserTickets")
    public UserTicketsDto buyTicketByUserId(Long userId, BuyTicketDto buyTicketDto) {

        LOGGER.info("inside buyTicketByUserId method");

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserTicketsDto userTicketsDto = apiClient.buy(buyTicketDto.getTicketHubId(), buyTicketDto.getEventCode(), existingUser.getId());
        return userTicketsDto;
    }

    @Override
    public UserTicketsDto getDefaultUserTickets(Long userId, BuyTicketDto buyTicketDto, Exception exception){

        LOGGER.info("inside getDefaultUserTickets method");

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserTicketsDto userTicketsDto = new UserTicketsDto();
        userTicketsDto.setPurchaseDate(LocalDate.now());
        userTicketsDto.setTicketHubName("Default Ticket Hub Name");
        userTicketsDto.setEventCode("Default Event Code");
        userTicketsDto.setLocation("Default Location");
        userTicketsDto.setLocalDate(LocalDate.parse("2023-12-31"));

        return userTicketsDto;
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        user.setEmail(userUpdateDto.getEmail());
        userRepository.save(user);
        return userConverter.toDto(user);
    }
}
