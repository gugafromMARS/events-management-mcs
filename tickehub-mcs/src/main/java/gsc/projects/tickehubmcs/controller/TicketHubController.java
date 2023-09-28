package gsc.projects.tickehubmcs.controller;

import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.dto.TicketUpdateDto;
import gsc.projects.tickehubmcs.service.TicketHubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickethub")
@AllArgsConstructor
public class TicketHubController {

    private TicketHubService ticketHubService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(ticketHubService.getAllTicketHubs());
    }

    @GetMapping("/{ticketHubId}")
    public ResponseEntity<?> getTicketHubById(@PathVariable ("ticketHubId") Long ticketHubId){
        return ResponseEntity.ok(ticketHubService.getById(ticketHubId));
    }

    @PostMapping
    public ResponseEntity<?> createTicketHub(@RequestBody TicketHubCreateDto ticketHubCreateDto){
        return new ResponseEntity<>(ticketHubService.create(ticketHubCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{ticketHubId}/buy/{eventCode}/{userId}")
    public ResponseEntity<?> buy(@PathVariable ("ticketHubId") Long ticketHubId,
                                 @PathVariable ("eventCode") String eventCode,
                                 @PathVariable ("userId") Long userId){
        return ResponseEntity.ok(ticketHubService.buyTicket(ticketHubId, eventCode, userId));
    }

    @DeleteMapping("/{ticketHubId}")
    public ResponseEntity<String> delete(@PathVariable ("ticketHubId") Long ticketHubId){
        ticketHubService.deleteTicketHub(ticketHubId);
        return new ResponseEntity<>("Ticket hub deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/{ticketHubId}")
    public ResponseEntity<?> update(@PathVariable ("ticketHubId") Long ticketHubId, @RequestBody TicketUpdateDto ticketUpdateDto){
        return ResponseEntity.ok(ticketHubService.updateTicketHub(ticketHubId, ticketUpdateDto));
    }


}
