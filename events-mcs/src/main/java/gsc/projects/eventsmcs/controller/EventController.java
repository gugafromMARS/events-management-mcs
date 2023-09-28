package gsc.projects.eventsmcs.controller;


import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.dto.EventUpdateDto;
import gsc.projects.eventsmcs.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private EventService eventService;


    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDto eventCreateDto){
        return new ResponseEntity<>(eventService.create(eventCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents(){
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/{eventCode}")
    public ResponseEntity<?> getAnEventByCode(@PathVariable ("eventCode") String eventCode){
        return ResponseEntity.ok(eventService.getByEventCode(eventCode));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> delete(@PathVariable ("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> update(@PathVariable ("eventId") Long eventId, @RequestBody EventUpdateDto eventUpdateDto){
        return ResponseEntity.ok(eventService.updateEvent(eventId, eventUpdateDto));
    }
}
