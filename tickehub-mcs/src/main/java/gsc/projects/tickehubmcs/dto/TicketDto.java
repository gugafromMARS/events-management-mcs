package gsc.projects.tickehubmcs.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TicketDto {

    private Long id;

    private String eventCode;

    private String location;

    private LocalDate localDate;
}
