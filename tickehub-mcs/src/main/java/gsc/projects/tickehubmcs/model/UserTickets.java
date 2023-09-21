package gsc.projects.tickehubmcs.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_tickets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDate purchaseDate;

    //TicketHubDto
    private Long ticketHubId;

    private String ticketHubName;

    private String ticketHubEmail;


    //TicketDto
    private Long ticketId;

    private String eventCode;

    private String location;

    private LocalDate localDate;

    //UserDto
    private Long userId;

    private String userName;

    private String userEmail;

}


