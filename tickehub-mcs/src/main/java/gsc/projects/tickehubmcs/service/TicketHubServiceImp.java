package gsc.projects.tickehubmcs.service;

import gsc.projects.tickehubmcs.converter.TicketHubConverter;
import gsc.projects.tickehubmcs.dto.TicketDto;
import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.dto.TicketHubDto;
import gsc.projects.tickehubmcs.model.TicketHub;
import gsc.projects.tickehubmcs.repository.TicketHubRepository;
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

    private APIClient apiClient;

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

    public TicketDto buyTicket(Long ticketHubId, String eventCode) {
        TicketHub existingTicketHub = ticketHubRepository.findById(ticketHubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Ticket Hub not found"));

        TicketDto ticketDto = apiClient.getTicket(eventCode);
        if(ticketDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket sold out");
        }
        return ticketDto;
    }
}
