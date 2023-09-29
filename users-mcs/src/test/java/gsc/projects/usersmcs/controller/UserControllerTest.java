package gsc.projects.usersmcs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gsc.projects.usersmcs.dto.*;
import gsc.projects.usersmcs.model.User;
import gsc.projects.usersmcs.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    UserCreateDto userCreateDto;
    UserDto userDto;
    User user;
    UserUpdateDto userUpdateDto;

    UserTicketsDto userTicketsDto;
    BuyTicketDto buyTicketDto;
    @BeforeEach
    void setUp (){
        userRepository.deleteAll();

        userCreateDto = new UserCreateDto();
        userCreateDto.setName("test");
        userCreateDto.setAge(18);
        userCreateDto.setEmail("test@email.com");

        userDto = new UserDto();
        userDto.setName(userCreateDto.getName());
        userDto.setEmail(userCreateDto.getEmail());
    }


    public void persistUser(){
        user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setAge(userCreateDto.getAge());
        userRepository.save(user);
    }

    public void buildUserUpdateDto(){
        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setEmail("newemail@email.com");
    }

    public void buildUserTicketsDto() {
        buyTicketDto = new BuyTicketDto();
        buyTicketDto.setTicketHubId(20L);
        buyTicketDto.setEventCode("testEvent");


        userTicketsDto = new UserTicketsDto();
        userTicketsDto.setPurchaseDate(LocalDate.now());
        userTicketsDto.setTicketHubId(20L);
        userTicketsDto.setTicketId(15L);
        userTicketsDto.setEventCode("testEvent");


    }
    @Nested
    @Tag("Crud tests")
    public class UserIntegrationTests {

        @Test
        @DisplayName("Create a valid user and return 200")
        public void createValidUser200() throws Exception {

            ResultActions response = mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userCreateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is(userDto.getName())))
                    .andExpect(jsonPath("$.email", is(userDto.getEmail())));
        }


        @Test
        @DisplayName("Try to create a user with an email that already exist and get 400")
        public void tryToCreateUserWithInvalidEmail400() throws Exception{

            persistUser();

            ResultActions response = mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userCreateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Get an user from id and return 200")
        public void getAnUserFromId200() throws Exception {

            persistUser();

            ResultActions response = mockMvc.perform(get("/users/{userId}", user.getId()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email", is(user.getEmail())));
        }

        @Test
        @DisplayName("Get an user with invalid id and get 404")
        public void getAnUserWithInvalidId404() throws Exception{

            ResultActions response = mockMvc.perform(get("/users/{userId}", 2L));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Get all users from users repository 200")
        public void getAllUsers200() throws Exception{

            persistUser();

            List<User> users = new ArrayList<>();
            users.add(user);

            ResultActions response = mockMvc.perform(get("/users"));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());

            assertEquals(1, users.size());
        }

        @Test
        @DisplayName("Delete a valid user and return 200")
        public void DeleteAValidUser200() throws Exception {

            persistUser();

            ResultActions response = mockMvc.perform(delete("/users/{userId}", user.getId()));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());

            ResultActions response2 = mockMvc.perform(get("/users/{userId}", user.getId()));

            response2.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());

        }

        @Test
        @DisplayName("Delete an invalid user and return 404")
        public void tryToDeleteInvalidUser404() throws Exception{

            ResultActions response = mockMvc.perform(delete("/users/{userId}", 2L));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Update a valid user and return 200")
        public void updateValidUser200() throws  Exception{

            persistUser();
            buildUserUpdateDto();

            ResultActions response = mockMvc.perform(put("/users/{userId}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email", is(userUpdateDto.getEmail())));

        }

        @Test
        @DisplayName("Try to update a invalid user and return 404")
        public void tryUpdateInvalidUser404() throws Exception {

            buildUserUpdateDto();

            ResultActions response = mockMvc.perform(put("/users/{userId}", 2L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userUpdateDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound());
        }


        @Test
        @DisplayName("Buy a ticket for an event and return 200")
        public void buyTicket200() throws Exception{

            persistUser();
            buildUserTicketsDto();

            ResultActions response = mockMvc.perform(get("/users/{userId}/buyticket", user.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(buyTicketDto)));

            response.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.eventCode", is("Default Event Code")));
        }
    }


}