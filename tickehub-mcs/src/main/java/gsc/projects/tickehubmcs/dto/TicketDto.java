package gsc.projects.tickehubmcs.dto;

import lombok.Getter;

import java.time.LocalDate;


@Getter
public class TicketDto {

    private Long id;

    private String eventCode;

    private String location;

    private LocalDate localDate;
}
