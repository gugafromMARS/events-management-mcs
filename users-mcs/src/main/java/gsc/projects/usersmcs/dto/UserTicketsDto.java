package gsc.projects.usersmcs.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class UserTicketsDto {
    private Long id;

    private LocalDate purchaseDate;

    private Long ticketHubId;

    private String ticketHubName;

    private Long ticketId;

    private String eventCode;

    private String location;

    private LocalDate localDate;
}
