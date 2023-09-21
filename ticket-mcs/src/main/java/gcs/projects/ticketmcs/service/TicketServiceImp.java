package gcs.projects.ticketmcs.service;

import gcs.projects.ticketmcs.converter.TicketConverter;
import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.dto.EventDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.model.TicketStatus;
import gcs.projects.ticketmcs.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImp {

    private TicketRepository ticketRepository;

    private TicketConverter ticketConverter;


    private APIClient apiClient;

    public TicketDto getAvailableTicket(String eventCode){
        List<Ticket> tickets = ticketRepository.findByEventCodeAndStatus(eventCode, TicketStatus.AVAILABLE);
        if(tickets.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets sold out");
        }
        Ticket availableTicket = tickets.get(0);
        availableTicket.setStatus(TicketStatus.SOLD);
        ticketRepository.save(availableTicket);
        return ticketConverter.toDto(availableTicket);
    }
    public List<TicketDto> create(CreateTicketDto createTicketDto){
        Ticket existingTicket = ticketRepository.findByEventCode(createTicketDto.getEventCode());

        if(existingTicket != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tickets for this event already exists");
        }

        EventDto eventDto = apiClient.getAnEventByCode(createTicketDto.getEventCode());

        List<TicketDto> ticketDtos = new ArrayList<>();
        for(int i = 0; i < eventDto.getMaxOfTickets(); i++) {
            Ticket newTicket = ticketConverter.fromCreateDto(eventDto);
            ticketRepository.save(newTicket);
            ticketDtos.add(ticketConverter.toDto(newTicket));
        }
        return ticketDtos;
    }

}
