package gcs.projects.ticketmcs.service;

import gcs.projects.ticketmcs.converter.TicketConverter;
import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.dto.TicketDto;
import gcs.projects.ticketmcs.dto.TicketUpdateDto;
import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.model.TicketStatus;
import gcs.projects.ticketmcs.repository.TicketRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TicketServiceImpTest {

    @Mock
    TicketRepository ticketRepository;
    @Mock
    TicketConverter ticketConverter;
    @InjectMocks
    TicketServiceImp ticketServiceImp;
    Ticket ticket;
    TicketDto ticketDto;

    CreateTicketDto createTicketDto;

    TicketUpdateDto ticketUpdateDto;

    @BeforeEach
    void setUp(){
        ticket = new Ticket();
        ticket.setEventCode("Test event code");
        ticket.setLocalDate(LocalDate.parse("2023-02-14"));
        ticket.setStatus(TicketStatus.AVAILABLE);

        ticketDto = new TicketDto();
        ticketDto.setEventCode(ticket.getEventCode());
        ticketDto.setLocalDate(LocalDate.parse("2023-02-14"));

        ticketUpdateDto = new TicketUpdateDto();
        ticketUpdateDto.setEventCode(ticket.getEventCode());
        ticketUpdateDto.setLocalDate(LocalDate.now());

        createTicketDto = new CreateTicketDto();
        createTicketDto.setEventCode(ticket.getEventCode());

    }


    @Nested
    @Tag("Unit tests")
    public class TicketUnitTests{

        @Test
        @DisplayName("Get an available ticket")
        public void getAnAvailableTicket(){


            given(ticketRepository.save(ticket)).willReturn(ticket);

            when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.ofNullable(ticket));

            assertEquals("Test event code", ticket.getEventCode());
            assertEquals(TicketStatus.AVAILABLE, ticket.getStatus());
        }


        @Test
        @DisplayName("Try to get a ticket but no one are available")
        public void tryToGetTicketButNoHaveAvailable(){

            ticket.setStatus(TicketStatus.SOLD);

            given(ticketRepository.save(ticket)).willReturn(ticket);

            when(ticketRepository.findById(ticket.getId())).thenThrow(ResponseStatusException.class);

            assertThrows(ResponseStatusException.class, () -> {
                ticketServiceImp.getAvailableTicket(ticket.getEventCode());
                    });

        }

        @Test
        @DisplayName("Update an exists ticket")
        public void updateAnExistsTicket() {

            List<Ticket> existingTickets = new ArrayList<>();
            existingTickets.add(ticket);

            List<TicketDto> ticketDtos = new ArrayList<>();
            ticketDto.setLocalDate(ticketUpdateDto.getLocalDate());
            ticketDtos.add(ticketDto);

            given(ticketRepository.save(ticket)).willReturn(ticket);

            given(ticketRepository.findByEventCode(ticket.getEventCode())).willReturn(existingTickets);

            when(ticketServiceImp.updateTicket(ticketUpdateDto)).thenReturn(ticketDtos);

            assertEquals(1, ticketDtos.size());

        }

        @Test
        @DisplayName("Try to update a ticket with a event code that not exists")
        public void tryToUpdateATicketThatNotExists() {
            ticketUpdateDto.setEventCode("Another code");

            given(ticketRepository.save(ticket)).willReturn(ticket);

            when(ticketRepository.findByEventCode(ticketUpdateDto.getEventCode())).thenThrow(ResponseStatusException.class);

            assertThrows(ResponseStatusException.class, () -> {
               ticketServiceImp.updateTicket(ticketUpdateDto);
            });
        }


        @Test
        @DisplayName("Try to create tickets that already exists for an event")
        public void tryToCreateTicketsForAnEventAlreadyExists(){

            List<TicketDto> ticketDtos = new ArrayList<>();
            ticketDtos.add(ticketDto);

            List<Ticket> tickets = new ArrayList<>();
            tickets.add(ticket);

            given(ticketRepository.save(ticket)).willReturn(ticket);

            when(ticketRepository.findByEventCode(createTicketDto.getEventCode())).thenThrow(ResponseStatusException.class);

            assertThrows(ResponseStatusException.class, () -> {
                ticketServiceImp.create(createTicketDto);
            });
        }
    }

}