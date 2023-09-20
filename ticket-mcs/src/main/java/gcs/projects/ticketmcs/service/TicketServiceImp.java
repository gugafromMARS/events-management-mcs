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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImp {

    private TicketRepository ticketRepository;

    private TicketConverter ticketConverter;

//    private RestTemplate restTemplate;
    private WebClient webClient;

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

//        ResponseEntity<EventDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/events/" + eventCode, EventDto.class);
//
//        EventDto eventDto = responseEntity.getBody();

        // Block = sync communication
        EventDto eventDto = webClient.get()
                .uri("http://localhost:8081/events/" + eventCode)
                .retrieve()
                .bodyToMono(EventDto.class)
                .block();

        List<TicketDto> ticketDtos = new ArrayList<>();
        for(int i = 0; i < eventDto.getMaxOfTickets(); i++) {
            Ticket newTicket = ticketConverter.fromCreateDto(eventDto);
            ticketRepository.save(newTicket);
            ticketDtos.add(ticketConverter.toDto(newTicket));
        }
        return ticketDtos;
    }

}
