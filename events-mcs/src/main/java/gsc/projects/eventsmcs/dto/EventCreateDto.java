package gsc.projects.eventsmcs.dto;

import gsc.projects.eventsmcs.model.EventType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EventCreateDto {

    private String name;

    private EventType type;

    private String location;

    private LocalDate localDate;

    private int tickets;

    private String eventCode;
}
