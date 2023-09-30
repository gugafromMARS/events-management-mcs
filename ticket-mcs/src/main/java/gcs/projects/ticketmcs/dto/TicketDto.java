package gcs.projects.ticketmcs.dto;


import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private Long id;

    private String eventCode;

    private String location;

    private LocalDate localDate;
}
