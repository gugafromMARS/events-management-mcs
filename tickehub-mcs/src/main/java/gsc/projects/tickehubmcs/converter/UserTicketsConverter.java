package gsc.projects.tickehubmcs.converter;


import gsc.projects.tickehubmcs.dto.TicketDto;
import gsc.projects.tickehubmcs.dto.TicketHubDto;
import gsc.projects.tickehubmcs.dto.UserDto;
import gsc.projects.tickehubmcs.dto.UserTicketsDto;
import gsc.projects.tickehubmcs.model.TicketHub;
import gsc.projects.tickehubmcs.model.UserTickets;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserTicketsConverter {


    public UserTicketsDto toDto(UserTickets userTickets){
        return UserTicketsDto.builder()
                .id(userTickets.getId())
                .purchaseDate(userTickets.getPurchaseDate())
                .ticketHubId(userTickets.getTicketHubId())
                .ticketHubName(userTickets.getTicketHubName())
                .ticketId(userTickets.getTicketId())
                .eventCode(userTickets.getEventCode())
                .location(userTickets.getLocation())
                .localDate(userTickets.getLocalDate())
                .build();
    }


    public UserTickets fromTicketHubDtoTicketDtoUserDto(TicketHub ticketHub,
                                                        TicketDto ticketDto,
                                                        UserDto userDto) {
        return UserTickets.builder()
                .purchaseDate(LocalDate.now())
                .ticketHubId(ticketHub.getId())
                .ticketHubName(ticketHub.getName())
                .ticketHubEmail(ticketHub.getEmail())
                .ticketId(ticketDto.getId())
                .eventCode(ticketDto.getEventCode())
                .location(ticketDto.getLocation())
                .localDate(ticketDto.getLocalDate())
                .userId(userDto.getId())
                .userName(userDto.getName())
                .userEmail(userDto.getEmail())
                .build();
    }

}
