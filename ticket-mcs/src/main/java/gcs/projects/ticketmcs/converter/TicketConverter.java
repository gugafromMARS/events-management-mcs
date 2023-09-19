package gcs.projects.ticketmcs.converter;

import gcs.projects.ticketmcs.dto.EventDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.model.TicketStatus;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    public TicketDto toDto(Ticket ticket){
        return TicketDto.builder()
                .id(ticket.getId())
                .eventCode(ticket.getEventCode())
                .location(ticket.getLocation())
                .localDate(ticket.getLocalDate())
                .build();
    }

    public Ticket fromCreateDto(EventDto eventDto){
        return Ticket.builder()
                .withEventCode(eventDto.getEventCode())
                .withLocation(eventDto.getLocation())
                .withLocalDate(eventDto.getLocalDate())
                .withStatus(TicketStatus.AVAILABLE)
                .build();
    }
}
