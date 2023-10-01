package gsc.projects.tickehubmcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.dto.TicketHubDto;
import gsc.projects.tickehubmcs.dto.TicketUpdateDto;
import gsc.projects.tickehubmcs.model.TicketHub;
import gsc.projects.tickehubmcs.repository.TicketHubRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class TicketHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TicketHubRepository ticketHubRepository;

    TicketHub ticketHub;
    TicketHubDto ticketHubDto;

    TicketHubCreateDto ticketHubCreateDto;

    TicketUpdateDto ticketUpdateDto;
    @BeforeEach
    void setUp(){
        ticketHubRepository.deleteAll();

        ticketHubCreateDto = new TicketHubCreateDto();
        ticketHubCreateDto.setName("Test name");
        ticketHubCreateDto.setEmail("test@email.com");

        ticketHub = new TicketHub();
        ticketHub.setName("Test name");
        ticketHub.setEmail("test@email.com");

        ticketHubDto = new TicketHubDto();
        ticketHubDto.setName("Test name");
        ticketHubDto.setEmail("test@email.com");

        ticketUpdateDto = new TicketUpdateDto();
        ticketUpdateDto.setEmail("newEmail@email.com");
    }

    void persistTicketHub(){
        ticketHubRepository.save(ticketHub);
    }

    @Nested
    @Tag("Crud tests")
    public class TicketHubIntegrationTests{


        @Test
        @DisplayName("Get all ticket hubs and return 200")
        public void getAllTicketHubs200() throws Exception{

            List<TicketHubDto> ticketHubDtos = new ArrayList<>();
            ticketHubDtos.add(ticketHubDto);

            persistTicketHub();

            ResultActions response = mockMvc.perform(get("/tickethub"));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());

            assertEquals(1, ticketHubDtos.size());

        }

        @Test
        @DisplayName("Get an exists ticket hub by id and return 200")
        public void getAnExistsTicketHub200() throws Exception {

            persistTicketHub();

            ResultActions response = mockMvc.perform(get("/tickethub/{ticketHubId}", ticketHub.getId()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Try to get a ticket hub that not exists and return 404")
        public void tryToGetTicketHubThatNotExists404() throws Exception{

            ResultActions response = mockMvc.perform(get("/tickethub/{ticketHubId}", ticketHub.getId()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());

        }

        @Test
        @DisplayName("Create a ticket hub and return 200")
        public void createATicketHub200() throws Exception {

            ResultActions response = mockMvc.perform(post("/tickethub")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketHubCreateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.email", is(ticketHub.getEmail())));
        }

        @Test
        @DisplayName("Try create a ticket hub that already exists")
        public void tryToCreateATicketThatAlreadyExists() throws Exception{

            persistTicketHub();

            ResultActions response = mockMvc.perform(post("/tickethub")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketHubCreateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Delete an exists Ticket hub and return 200")
        public void deleteAnExistsTicketHub200() throws Exception{

            persistTicketHub();

            ResultActions response = mockMvc.perform(delete("/tickethub/{ticketHubId}", ticketHub.getId()));

           response.andDo(MockMvcResultHandlers.print())
                   .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Try to delete a ticket hub that not exists 404")
        public void tryToDeleteATicketHubThatNotExists404() throws Exception{

            ResultActions response = mockMvc.perform(delete("/tickethub/{ticketHubId}", ticketHub.getId()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Update an exists ticket hub and return 200")
        public void updateAnExistsTicketHub200()  throws  Exception{

            persistTicketHub();

            ResultActions response = mockMvc.perform(put("/tickethub/{ticketHubId}", ticketHub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email", is(ticketUpdateDto.getEmail())));
        }

        @Test
        @DisplayName("Try to update and non exist ticket hub return 404")
        public void tryToUpdateNonExistsTicketHub404() throws Exception{

            ResultActions response = mockMvc.perform(put("/tickethub/{ticketHubId}", ticketHub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }


    }
}