package gsc.projects.usersmcs.service;

import gsc.projects.usersmcs.converter.UserConverter;
import gsc.projects.usersmcs.dto.BuyTicketDto;
import gsc.projects.usersmcs.dto.UserCreateDto;
import gsc.projects.usersmcs.dto.UserDto;
import gsc.projects.usersmcs.dto.UserTicketsDto;
import gsc.projects.usersmcs.model.User;
import gsc.projects.usersmcs.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
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


    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultUserTickets")
    public UserTicketsDto buyTicketByUserId(Long userId, BuyTicketDto buyTicketDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserTicketsDto userTicketsDto = apiClient.buy(buyTicketDto.getTicketHubId(), buyTicketDto.getEventCode(), existingUser.getId());
        return userTicketsDto;
    }

    public UserTicketsDto getDefaultUserTickets(Long userId, BuyTicketDto buyTicketDto, Exception exception){
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
}
