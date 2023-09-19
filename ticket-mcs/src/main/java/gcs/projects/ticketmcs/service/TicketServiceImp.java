package gcs.projects.ticketmcs.service;

import gcs.projects.ticketmcs.converter.TicketConverter;
import gcs.projects.ticketmcs.dto.EventDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImp {

    private TicketRepository ticketRepository;

    private TicketConverter ticketConverter;

    private RestTemplate restTemplate;

    public List<TicketDto> getAll(String eventCode){
        return ticketRepository.findAllByEventCode(eventCode).stream()
                .map(ticket -> ticketConverter.toDto(ticket))
                .toList();
    }
    public List<TicketDto> create(String eventCode){
        Ticket existingTicket = ticketRepository.findByEventCode(eventCode);

        if(existingTicket != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket for this event already exists");
        }

        ResponseEntity<EventDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/events/" + eventCode, EventDto.class);

        EventDto eventDto = responseEntity.getBody();

        List<TicketDto> ticketDtos = new ArrayList<>();
        for(int i = 0; i < eventDto.getMaxOfTickets(); i++) {
            Ticket newTicket = ticketConverter.fromCreateDto(eventDto);
            ticketRepository.save(newTicket);
            ticketDtos.add(ticketConverter.toDto(newTicket));
        }
        return ticketDtos;
    }

    public TicketDto getById(Long ticketId) {
        return ticketRepository.findById(ticketId).stream()
                .map(ticket -> ticketConverter.toDto(ticket))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }
}
