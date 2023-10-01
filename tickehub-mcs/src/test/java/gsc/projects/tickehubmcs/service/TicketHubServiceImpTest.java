package gsc.projects.tickehubmcs.service;

import gsc.projects.tickehubmcs.converter.TicketHubConverter;
import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.dto.TicketHubDto;
import gsc.projects.tickehubmcs.dto.TicketUpdateDto;
import gsc.projects.tickehubmcs.model.TicketHub;
import gsc.projects.tickehubmcs.repository.TicketHubRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class TicketHubServiceImpTest {

    @Mock
    TicketHubRepository ticketHubRepository;

    @Mock
    TicketHubConverter ticketHubConverter;

    @InjectMocks
    TicketHubServiceImp ticketHubServiceImp;

    TicketHub  ticketHub;
    TicketHubDto ticketHubDto;

    TicketHubCreateDto ticketHubCreateDto;

    TicketUpdateDto ticketUpdateDto;
    @BeforeEach
    void setUp(){
        ticketHubRepository.deleteAll();

        ticketHub = new TicketHub();
        ticketHub.setEmail("test@email.com");
        ticketHub.setName("test name");

        ticketHubDto = new TicketHubDto();
        ticketHubDto.setEmail("test@email.com");
        ticketHubDto.setName("test name");

        ticketHubCreateDto = new TicketHubCreateDto();
        ticketHubCreateDto.setEmail("test@email.com");

        ticketUpdateDto = new TicketUpdateDto();
        ticketUpdateDto.setEmail("newEmail@email.com");
    }


    @Nested
    @Tag("Unit tests")
    public class TicketHubUnitTests {

        @Test
        @DisplayName("Create a ticket hub")
        public void createATicketHub(){

            given(ticketHubRepository.findById(ticketHub.getId())).willReturn(null);

            given(ticketHubRepository.save(ticketHub)).willReturn(ticketHub);

            when(ticketHubRepository.findById(ticketHub.getId())).thenReturn(Optional.ofNullable(ticketHub));

            ticketHubDto.setId(ticketHub.getId());

            assertEquals(ticketHubDto.getId(), ticketHub.getId());
        }

        @Test
        @DisplayName("Try to create an exists Ticket hub ")
        public void tryToCreateAnExistsTicketHub() {
            TicketHub ticketHub2 = new TicketHub();
            ticketHub2.setEmail(ticketHub.getEmail());

            given(ticketHubRepository.save(ticketHub)).willReturn(ticketHub);

            given(ticketHubRepository.findByEmail(ticketHub.getEmail())).willReturn(ticketHub);

            when(ticketHubRepository.save(ticketHub2)).thenThrow(ResponseStatusException.class);

            assertThrows(ResponseStatusException.class, () -> {
                ticketHubServiceImp.create(ticketHubCreateDto);
            });
        }

        @Test
        @DisplayName("Delete an exists Ticket hub")
        public void deleteAnExistsTicketHub() {

            given(ticketHubRepository.save(ticketHub)).willReturn(ticketHub);

            given(ticketHubRepository.findById(ticketHub.getId())).willReturn(Optional.ofNullable(ticketHub));

            ticketHubRepository.delete(ticketHub);

            when(ticketHubRepository.findById(ticketHub.getId())).thenReturn(null);

            assertNull(ticketHub.getId());
        }

        @Test
        @DisplayName("Update an exists ticket hub")
        public void updateAnExistsTicketHub(){

            given(ticketHubRepository.save(ticketHub)).willReturn(ticketHub);

            given(ticketHubRepository.findById(ticketHub.getId())).willReturn(Optional.ofNullable(ticketHub));

            ticketHubDto.setEmail(ticketUpdateDto.getEmail());
            when(ticketHubServiceImp.updateTicketHub(ticketHub.getId(), ticketUpdateDto)).thenReturn(ticketHubDto);

            assertEquals(ticketHubDto.getEmail(), ticketUpdateDto.getEmail());
        }

        @Test
        @DisplayName("Try to update not exists ticket hub")
        public void tryToUpdateNotExistsTicketHub() {

            given(ticketHubRepository.save(ticketHub)).willReturn(ticketHub);

            when(ticketHubRepository.findById(2L)).thenThrow(ResponseStatusException.class);

            assertThrows(ResponseStatusException.class, () ->{
                ticketHubServiceImp.updateTicketHub(2L, ticketUpdateDto);
            });
        }

    }
}