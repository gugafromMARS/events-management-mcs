package gsc.projects.usersmcs.service;

import gsc.projects.usersmcs.dto.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getById(Long userId);

    UserDto create(UserCreateDto userCreateDto);

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultUserTickets")
    UserTicketsDto buyTicketByUserId(Long userId, BuyTicketDto buyTicketDto);

    UserTicketsDto getDefaultUserTickets(Long userId, BuyTicketDto buyTicketDto, Exception exception);

    void deleteUserById(Long userId);

    UserDto updateUser(Long userId, UserUpdateDto userUpdateDto);
}
