package gsc.projects.eventsmcs.service;


import gsc.projects.eventsmcs.converter.EventConverter;
import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.dto.EventDto;
import gsc.projects.eventsmcs.dto.EventUpdateDto;
import gsc.projects.eventsmcs.model.Event;
import gsc.projects.eventsmcs.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImp implements EventService {

    private EventRepository eventRepository;

    private EventConverter eventConverter;

    @Override
    public EventDto create(EventCreateDto eventCreateDto){
        Event existingEvent = eventRepository.findByEventCode(eventCreateDto.getEventCode());
        if(existingEvent != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event already exists");
        }
        Event newEvent = eventConverter.fromCreateDto(eventCreateDto);
        eventRepository.save(newEvent);
        return eventConverter.toDto(newEvent);
    }

    @Override
    public List<EventDto> getAll() {
        return eventRepository.findAll().stream()
                .map(event -> eventConverter.toDto(event))
                .toList();
    }

    @Override
    public EventDto getByEventCode(String eventCode) {

        Event existingEvent = eventRepository.findByEventCode(eventCode);
        if(existingEvent == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        return eventConverter.toDto(existingEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        eventRepository.delete(event);
    }

    @Override
    public EventDto updateEvent(Long eventId, EventUpdateDto eventUpdateDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        event.setLocalDate(eventUpdateDto.getLocalDate());
        eventRepository.save(event);
        return eventConverter.toDto(event);
    }
}
