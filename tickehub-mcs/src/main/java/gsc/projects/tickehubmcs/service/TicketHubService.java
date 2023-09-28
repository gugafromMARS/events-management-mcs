package gsc.projects.tickehubmcs.service;

import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.dto.TicketHubDto;
import gsc.projects.tickehubmcs.dto.TicketUpdateDto;
import gsc.projects.tickehubmcs.dto.UserTicketsDto;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.List;

public interface TicketHubService {
    List<TicketHubDto> getAllTicketHubs();

    TicketHubDto getById(Long ticketHubId);

    TicketHubDto create(TicketHubCreateDto ticketHubCreateDto);

    //    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultUserTickets")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultUserTickets")
    UserTicketsDto buyTicket(Long ticketHubId, String eventCode, Long userId);

    UserTicketsDto getDefaultUserTickets(Long ticketHubId, String eventCode, Long userId, Exception exception);

    void deleteTicketHub(Long ticketHubId);

    TicketHubDto updateTicketHub(Long ticketHubId, TicketUpdateDto ticketUpdateDto);
}
