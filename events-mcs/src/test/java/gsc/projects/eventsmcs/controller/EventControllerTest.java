package gsc.projects.eventsmcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.dto.EventDto;
import gsc.projects.eventsmcs.dto.EventUpdateDto;
import gsc.projects.eventsmcs.model.Event;
import gsc.projects.eventsmcs.repository.EventRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class EventControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Event event;

    EventCreateDto eventCreateDto;

    EventDto eventDto;

    EventUpdateDto eventUpdateDto;

    @BeforeEach
    void setUp(){
        eventRepository.deleteAll();

        eventCreateDto = new EventCreateDto();
        eventCreateDto.setEventCode("Event Code Test");
        eventCreateDto.setLocalDate(LocalDate.parse("2023-02-14"));

        event = new Event();
        event.setEventCode("Event Code Test");
        event.setLocalDate(LocalDate.parse("2023-02-14"));

        eventDto = new EventDto();
        eventDto.setEventCode("Event Code Test");
        eventDto.setLocalDate(LocalDate.parse("2023-02-14"));

        eventUpdateDto = new EventUpdateDto();
        eventUpdateDto.setLocalDate(LocalDate.now());
    }


    public void persistEvent(){
        eventRepository.save(event);
    }

    @Nested
    @Tag("Crud tests")
    public class EventCrudTests {


        @Test
        @DisplayName("Create a valid event and return 200")
        public void createAnValidEvent200() throws Exception{

            ResultActions response = mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventCreateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.eventCode", is(eventDto.getEventCode())));
        }

        @Test
        @DisplayName("Try to create exists event and get 400")
        public void tryCreateExistsEvent400() throws Exception{

            persistEvent();

            ResultActions response = mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventCreateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Get a exist event by id and return 200")
        public void getAnExistEvent200() throws Exception{

            persistEvent();

            ResultActions response = mockMvc.perform(get("/events/{eventCode}", event.getEventCode()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.eventCode", is(eventDto.getEventCode())));
        }

        @Test
        @DisplayName("Try get an invalid event by id and return 404")
        public void tryGetInvalidEvent404() throws Exception{

            persistEvent();

            ResultActions response = mockMvc.perform(get("/events/{eventCode}", "test"));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Delete a valid Event and return 200")
        public void deleteValidEvent200() throws Exception{

            persistEvent();

            ResultActions response = mockMvc.perform(delete("/events/{eventId}", event.getId()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());

            ResultActions response2 = mockMvc.perform(get("/events/{eventCode}", event.getEventCode()));

            response2.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Try to delete an invalid Event and return 404")
        public void tryDeleteInvalidEvent404() throws Exception {

            ResultActions response = mockMvc.perform(delete("/events/{eventId}", 2L));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Update a valid Event and return 200")
        public void updateValidEvent200() throws Exception {

            persistEvent();

            ResultActions response = mockMvc.perform(put("/events/{eventId}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.localDate", is(eventUpdateDto.getLocalDate().toString())));
        }

        @Test
        @DisplayName("Try to update a invalid Event and return 404")
        public void TryToUpdateInvalidEvent404() throws Exception{

            persistEvent();

            ResultActions response = mockMvc.perform(put("/events/{eventId}", 2L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }
    }

}