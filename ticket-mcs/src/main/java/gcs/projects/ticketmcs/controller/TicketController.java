package gcs.projects.ticketmcs.controller;


import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.dto.EventDto;
import gcs.projects.ticketmcs.service.TicketServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    private TicketServiceImp ticketServiceImp;

    @GetMapping ("/{eventCode}")
    public ResponseEntity<?> getTicket(@PathVariable ("eventCode") String eventCode){
        return ResponseEntity.ok(ticketServiceImp.getAvailableTicket(eventCode));
    }

    @PostMapping()
    public ResponseEntity<?> createTickets(@RequestBody CreateTicketDto createTicketDto){
        return new ResponseEntity<>(ticketServiceImp.create(createTicketDto), HttpStatus.CREATED);
    }
}
