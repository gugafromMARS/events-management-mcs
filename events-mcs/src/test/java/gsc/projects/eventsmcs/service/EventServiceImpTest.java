package gsc.projects.eventsmcs.service;

import gsc.projects.eventsmcs.converter.EventConverter;
import gsc.projects.eventsmcs.dto.EventDto;
import gsc.projects.eventsmcs.dto.EventUpdateDto;
import gsc.projects.eventsmcs.model.Event;
import gsc.projects.eventsmcs.repository.EventRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventServiceImpTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    EventConverter eventConverter;

    @InjectMocks
    EventServiceImp eventServiceImp;

    Event event;
    EventDto eventDto;
    Event event2;

    EventUpdateDto eventUpdateDto;

    @BeforeEach
    void setUp(){
        event = new Event();
        event.setEventCode("Test Event Code");
        event.setLocalDate(LocalDate.now());

        event2 = new Event();
        event.setEventCode(event.getEventCode());

        eventDto = new EventDto();
        eventDto.setEventCode(event.getEventCode());
        eventDto.setLocalDate(event.getLocalDate());
    }

    void buildEventUpdateDto(){

        eventUpdateDto = new EventUpdateDto();
        eventUpdateDto.setLocalDate(LocalDate.parse("2023-05-12"));

    }

    @Nested
    @Tag("Unit tests")
    public class EventUnitTests {


        @Test
        @DisplayName("Create an valid event")
        public void createAnValidEvent() {

            given(eventRepository.findById(event.getId())).willReturn(null);

            when(eventRepository.save(event)).thenReturn(event);

            eventDto.setId(event.getId());

            assertEquals(eventDto.getId(), event.getId());
        }

        @Test
        @DisplayName("Try to create exists event")
        public void tryCreateInvalidEvent(){

            given(eventRepository.save(event)).willReturn(event);

            when(eventRepository.save(event)).thenReturn(null);

            assertEquals(eventDto.getEventCode(), event.getEventCode());
        }

        @Test
        @DisplayName("Delete an exists event")
        public void deleteAnExistsEvent(){

            given(eventRepository.save(event)).willReturn(event);

            when(eventRepository.findById(event.getId())).thenReturn(Optional.ofNullable(event));

            eventRepository.delete(event);

            assertNull(event.getId());
        }

        @Test
        @DisplayName("Update an exists event")
        public void updateAnExistsEvent() {
            buildEventUpdateDto();

            given(eventRepository.save(event)).willReturn(event);

            given(eventRepository.findById(event.getId())).willReturn(Optional.ofNullable(event));

            when(eventServiceImp.updateEvent(event.getId(), eventUpdateDto)).thenReturn(eventDto);

            assertEquals(event.getLocalDate(), eventUpdateDto.getLocalDate());
        }


    }


}