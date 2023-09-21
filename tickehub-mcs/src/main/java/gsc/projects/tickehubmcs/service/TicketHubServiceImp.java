package gsc.projects.tickehubmcs.service;

import gsc.projects.tickehubmcs.converter.TicketHubConverter;
import gsc.projects.tickehubmcs.converter.UserTicketsConverter;
import gsc.projects.tickehubmcs.dto.*;
import gsc.projects.tickehubmcs.model.TicketHub;
import gsc.projects.tickehubmcs.model.UserTickets;
import gsc.projects.tickehubmcs.repository.TicketHubRepository;
import gsc.projects.tickehubmcs.repository.UserTicketsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketHubServiceImp {


    private TicketHubRepository ticketHubRepository;

    private TicketHubConverter ticketHubConverter;

    private APITicket apiTicket;

    private APIUser apiUser;

    private UserTicketsConverter userTicketsConverter;
    private final UserTicketsRepository userTicketsRepository;

    public List<TicketHubDto> getAllTicketHubs() {
        return ticketHubRepository.findAll().stream()
                .map(ticketHub -> ticketHubConverter.toDto(ticketHub))
                .toList();
    }

    public TicketHubDto getById(Long ticketHubId) {
        return ticketHubRepository.findById(ticketHubId)
                .map(ticketHub -> ticketHubConverter.toDto(ticketHub))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket Hub not found"));
    }

    public TicketHubDto create(TicketHubCreateDto ticketHubCreateDto) {
        TicketHub existingTicketHub = ticketHubRepository.findByEmail(ticketHubCreateDto.getEmail());
        if(existingTicketHub != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket Hub already exists");
        }
        TicketHub newTicketHub = ticketHubConverter.fromCreateDto(ticketHubCreateDto);
        ticketHubRepository.save(newTicketHub);
        return ticketHubConverter.toDto(newTicketHub);
    }

    public UserTicketsDto buyTicket(Long ticketHubId, String eventCode, Long userId) {

        TicketHub existingTicketHub = ticketHubRepository.findById(ticketHubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Ticket Hub not found"));

        TicketDto ticketDto = apiTicket.getTicket(eventCode);

        UserDto userDto = apiUser.getUserById(userId);
        if(userDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(ticketDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket sold out");
        }

        UserTickets userTickets = userTicketsConverter.fromTicketHubDtoTicketDtoUserDto(existingTicketHub, ticketDto, userDto);
        userTicketsRepository.save(userTickets);
        return userTicketsConverter.toDto(userTickets);
    }
}
