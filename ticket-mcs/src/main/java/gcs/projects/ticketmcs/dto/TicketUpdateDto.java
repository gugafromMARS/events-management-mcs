package gcs.projects.ticketmcs.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TicketUpdateDto {

    private String eventCode;

    private LocalDate localDate;
}
