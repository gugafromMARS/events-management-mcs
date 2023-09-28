package gsc.projects.eventsmcs.service;

import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.dto.EventDto;
import gsc.projects.eventsmcs.dto.EventUpdateDto;

import java.util.List;

public interface EventService {
    EventDto create(EventCreateDto eventCreateDto);

    List<EventDto> getAll();

    EventDto getByEventCode(String eventCode);

    void deleteEvent(Long eventId);

    EventDto updateEvent(Long eventId, EventUpdateDto eventUpdateDto);
}
