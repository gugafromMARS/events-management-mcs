package gcs.projects.ticketmcs.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
public class TicketDto {

    private Long id;

    private String eventCode;

    private String location;




    private LocalDate localDate;
}
