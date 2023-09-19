package gcs.projects.ticketmcs.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private String location;
    private LocalDate localDate;
    private int maxOfTickets;
    private String eventCode;
}
