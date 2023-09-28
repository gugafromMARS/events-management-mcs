package gcs.projects.ticketmcs.service;

import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.List;

public interface TicketService {
    TicketDto getAvailableTicket(String eventCode);

    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultEvent")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultEvent")
    List<TicketDto> create(CreateTicketDto createTicketDto);

    List<TicketDto> getDefaultEvent(CreateTicketDto createTicketDto, Exception exception);
}
