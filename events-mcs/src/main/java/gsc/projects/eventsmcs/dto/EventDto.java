package gsc.projects.eventsmcs.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private String location;
    private LocalDate localDate;
    private int maxOfTickets;
    private String eventCode;
}
