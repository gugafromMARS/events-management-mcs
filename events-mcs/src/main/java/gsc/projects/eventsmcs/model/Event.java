package gsc.projects.eventsmcs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name ="event")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType type;

    private String location;

    @Column(name = "local_date")
    private LocalDate localDate;

    private int maxOfTickets;

    @Column(name = "event_code")
    private String eventCode;


    public static EventBuilder builder() {
        return new EventBuilder();
    }
    public static class EventBuilder{

        private Event event;

        public EventBuilder() {
            event = new Event();
        }

        public EventBuilder withName(String name){
            event.setName(name);
            return this;
        }

        public EventBuilder withType(EventType eventType){
            event.setType(eventType);
            return this;
        }

        public EventBuilder withLocation(String location){
            event.setLocation(location);
            return this;
        }
        public EventBuilder withDate(LocalDate localDate){
            event.setLocalDate(localDate);
            return this;
        }

        public EventBuilder withTickets(int numOfTickets){
            event.setMaxOfTickets(numOfTickets);
            return this;
        }

        public EventBuilder withEventCode(String eventCode){
            event.setEventCode(eventCode);
            return this;
        }

        public Event build(){
            return event;
        }

    }
}
