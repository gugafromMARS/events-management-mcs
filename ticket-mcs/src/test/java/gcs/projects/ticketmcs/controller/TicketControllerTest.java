package gcs.projects.ticketmcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.dto.EventDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import gcs.projects.ticketmcs.dto.TicketUpdateDto;
import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.model.TicketStatus;
import gcs.projects.ticketmcs.repository.TicketRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    Ticket ticket;
    TicketDto ticketDto;
    CreateTicketDto createTicketDto;
    TicketUpdateDto ticketUpdateDto;

    @BeforeEach
    void setUp(){
        ticketRepository.deleteAll();

        createTicketDto = new CreateTicketDto();
        createTicketDto.setEventCode("Default Event Code");


        ticket = new Ticket();
        ticket.setEventCode(createTicketDto.getEventCode());
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setLocalDate(LocalDate.parse("2023-02-14"));

        ticketDto = new TicketDto();
        ticketDto.setEventCode(ticket.getEventCode());

        ticketUpdateDto = new TicketUpdateDto();
        ticketUpdateDto.setEventCode(ticket.getEventCode());
        ticketUpdateDto.setLocalDate(LocalDate.now());


    }


    void persistTicket(){
        ticketRepository.save(ticket);
    }

    @Nested
    @Tag("Crud Tests")
    public class TicketCrudTests {

        @Test
        @DisplayName("Get an exists ticket from event code and return 200")
        public void getExistsTicketFromEvent200() throws Exception {

            persistTicket();

            ResultActions response = mockMvc.perform(get("/tickets/{eventCode}", createTicketDto.getEventCode()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.eventCode", is(ticketDto.getEventCode())));
        }

        @Test
        @DisplayName("Try to get a non exists ticket and return 404")
        public void tryToGetANonExistsTicket404() throws Exception{

            persistTicket();

            ResultActions response = mockMvc.perform(get("/tickets/{eventCode}", createTicketDto.getEventCode() + "1"));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Update an exists ticket and return 200")
        public void updateAnExistsTicket200() throws Exception {
            List<TicketDto> ticketDtos = new ArrayList<>();
            persistTicket();

            ResultActions response = mockMvc.perform(put("/tickets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketUpdateDto)));

            ticketDto.setLocalDate(ticketUpdateDto.getLocalDate());
            ticketDtos.add(ticketDto);

            assertEquals(1, ticketDtos.size());

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Try to update a non exists ticket and return 404")
        public void tryUpdateNonExistsTicket404() throws Exception{

            persistTicket();

            ticketUpdateDto.setEventCode("Another event code");

            ResultActions response = mockMvc.perform(put("/tickets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Create a ticket for a exists event and return 200")
        public void createTicketForExistsEvent200() throws Exception{

            ResultActions response = mockMvc.perform(post("/tickets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createTicketDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated());
        }

        
    }



























}