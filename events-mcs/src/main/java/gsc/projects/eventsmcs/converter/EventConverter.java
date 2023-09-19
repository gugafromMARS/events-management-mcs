package gsc.projects.eventsmcs.converter;


import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.dto.EventDto;
import gsc.projects.eventsmcs.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventConverter {

    public EventDto toDto(Event event){
        return new EventDto().builder()
                .id(event.getId())
                .name(event.getName())
                .location(event.getLocation())
                .localDate(event.getLocalDate())
                .maxOfTickets(event.getMaxOfTickets())
                .eventCode(event.getEventCode())
                .build();
    }

    public Event fromCreateDto(EventCreateDto eventCreateDto){
        return Event.builder()
                .withName(eventCreateDto.getName())
                .withType(eventCreateDto.getType())
                .withDate(eventCreateDto.getLocalDate())
                .withLocation(eventCreateDto.getLocation())
                .withTickets(eventCreateDto.getTickets())
                .withEventCode(eventCreateDto.getEventCode())
                .build();
    }
}
