package gsc.projects.tickehubmcs.converter;


import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.dto.TicketHubDto;
import gsc.projects.tickehubmcs.model.TicketHub;
import org.springframework.stereotype.Component;

@Component
public class TicketHubConverter {


    public TicketHubDto toDto(TicketHub ticketHub){
        return new TicketHubDto().builder()
                .id(ticketHub.getId())
                .name(ticketHub.getName())
                .email(ticketHub.getEmail())
                .build();
    }

    public TicketHub fromCreateDto(TicketHubCreateDto ticketHubCreateDto){
        return TicketHub.builder()
                .withName(ticketHubCreateDto.getName())
                .withEmail(ticketHubCreateDto.getEmail())
                .build();
    }
}
