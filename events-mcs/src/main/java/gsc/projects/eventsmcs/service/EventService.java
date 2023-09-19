package gsc.projects.eventsmcs.service;

import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto create(EventCreateDto eventCreateDto);

    List<EventDto> getAll();

}
