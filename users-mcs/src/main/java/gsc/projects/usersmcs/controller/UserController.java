package gsc.projects.usersmcs.controller;


import gsc.projects.usersmcs.dto.BuyTicketDto;
import gsc.projects.usersmcs.dto.UserCreateDto;
import gsc.projects.usersmcs.service.UserServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {


    private UserServiceImp userServiceImp;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userServiceImp.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable ("userId") Long userId){
        return ResponseEntity.ok(userServiceImp.getById(userId));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto){
        return new ResponseEntity<>(userServiceImp.create(userCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/buyticket")
    public ResponseEntity<?> buyTicket(@PathVariable ("userId") Long userId,@RequestBody BuyTicketDto buyTicketDto){
        return new ResponseEntity<>(userServiceImp.buyTicketByUserId(userId, buyTicketDto), HttpStatus.CREATED);
    }
}
