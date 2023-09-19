package gcs.projects.ticketmcs.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String eventCode;

    private String location;

    private LocalDate localDate;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public static TicketBuilder builder() {
        return new TicketBuilder();
    }


    public static class TicketBuilder {

        private Ticket ticket;

        public TicketBuilder() {
            ticket = new Ticket();
        }

        public TicketBuilder withEventCode(String eventCode){
            ticket.setEventCode(eventCode);
            return this;
        }

        public TicketBuilder withLocation(String location){
            ticket.setLocation(location);
            return this;
        }

        public TicketBuilder withLocalDate(LocalDate localDate){
            ticket.setLocalDate(localDate);
            return this;
        }
        public TicketBuilder withStatus(TicketStatus ticketStatus){
            ticket.setStatus(ticketStatus);
            return this;
        }

        public Ticket build(){
            return ticket;
        }
    }
}
