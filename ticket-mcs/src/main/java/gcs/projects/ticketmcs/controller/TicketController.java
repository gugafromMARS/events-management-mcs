package gcs.projects.ticketmcs.controller;


import gcs.projects.ticketmcs.dto.CreateTicketDto;
import gcs.projects.ticketmcs.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    private TicketService ticketService;

    @GetMapping ("/{eventCode}")
    public ResponseEntity<?> getTicket(@PathVariable ("eventCode") String eventCode){
        return ResponseEntity.ok(ticketService.getAvailableTicket(eventCode));
    }

    @PostMapping()
    public ResponseEntity<?> createTickets(@RequestBody CreateTicketDto createTicketDto){
        return new ResponseEntity<>(ticketService.create(createTicketDto), HttpStatus.CREATED);
    }
}
