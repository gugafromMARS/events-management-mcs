package gsc.projects.tickehubmcs.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketHubDto {

    private Long id;

    private String name;

    private String email;

}
