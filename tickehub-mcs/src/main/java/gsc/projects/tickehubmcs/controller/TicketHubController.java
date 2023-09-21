package gsc.projects.tickehubmcs.controller;

import gsc.projects.tickehubmcs.dto.TicketHubCreateDto;
import gsc.projects.tickehubmcs.service.TicketHubServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickethub")
@AllArgsConstructor
public class TicketHubController {

    private TicketHubServiceImp ticketHubServiceImp;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(ticketHubServiceImp.getAllTicketHubs());
    }

    @GetMapping("/{ticketHubId}")
    public ResponseEntity<?> getTicketHubById(@PathVariable ("ticketHubId") Long ticketHubId){
        return ResponseEntity.ok(ticketHubServiceImp.getById(ticketHubId));
    }

    @PostMapping
    public ResponseEntity<?> createTicketHub(@RequestBody TicketHubCreateDto ticketHubCreateDto){
        return new ResponseEntity<>(ticketHubServiceImp.create(ticketHubCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{ticketHubId}/buy/{eventCode}")
    public ResponseEntity<?> buy(@PathVariable ("ticketHubId") Long ticketHubId, @PathVariable ("eventCode") String eventCode){
        return ResponseEntity.ok(ticketHubServiceImp.buyTicket(ticketHubId, eventCode));
    }


}
