package gsc.projects.tickehubmcs.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tickethub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String email;

    private int maxOfTickets;

    private int currentNumOfTickets;

    public static TicketHubBuilder builder() {
        return new TicketHubBuilder();
    }

    public static class TicketHubBuilder{

        private TicketHub ticketHub;

        public TicketHubBuilder() {
            ticketHub = new TicketHub();
        }

        public TicketHubBuilder withName(String name){
            ticketHub.setName(name);
            return this;
        }

        public TicketHubBuilder withEmail(String email){
            ticketHub.setEmail(email);
            return this;
        }
        public TicketHub build(){
            return ticketHub;
        }

    }
}
