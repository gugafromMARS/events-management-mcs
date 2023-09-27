package gcs.projects.ticketmcs.service;

import gcs.projects.ticketmcs.converter.TicketConverter;
import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.dto.EventDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.model.TicketStatus;
import gcs.projects.ticketmcs.repository.TicketRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImp {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImp.class);

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


    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultEvent")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultEvent")
    public List<TicketDto> create(CreateTicketDto createTicketDto){

        LOGGER.info("inside create method");
        List<Ticket> existingTickets = ticketRepository.findByEventCode(createTicketDto.getEventCode());

        if(existingTickets.size() != 0){
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

    public List<TicketDto> getDefaultEvent(CreateTicketDto createTicketDto, Exception exception){
        List<Ticket> existingTickets = ticketRepository.findByEventCode(createTicketDto.getEventCode());

        LOGGER.info("inside getDefaultEvent method");
        if(existingTickets.size() != 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tickets for this event already exists");
        }

        //instead of make api call to external service, we make a default eventDto.
        EventDto eventDto = new EventDto();
        eventDto.setName("DefaultEvent");
        eventDto.setLocation("DefaultLocation");
        eventDto.setEventCode("DefaultCode");
        eventDto.setLocalDate(LocalDate.parse("2023-12-31"));
        eventDto.setMaxOfTickets(2);

        List<TicketDto> ticketDtos = new ArrayList<>();
        for(int i = 0; i < eventDto.getMaxOfTickets(); i++) {
            Ticket newTicket = ticketConverter.fromCreateDto(eventDto);
            ticketDtos.add(ticketConverter.toDto(newTicket));
        }
        return ticketDtos;
    }

}
