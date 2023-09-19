package gsc.projects.eventsmcs.controller;


import gsc.projects.eventsmcs.dto.EventCreateDto;
import gsc.projects.eventsmcs.service.EventServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private EventServiceImp eventServiceImp;


    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDto eventCreateDto){
        return new ResponseEntity<>(eventServiceImp.create(eventCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents(){
        return ResponseEntity.ok(eventServiceImp.getAll());
    }


    @GetMapping("/{eventCode}")
    public ResponseEntity<?> getAnEventByCode(@PathVariable ("eventCode") String eventCode){
        return ResponseEntity.ok(eventServiceImp.getByEventCode(eventCode));
    }
}
