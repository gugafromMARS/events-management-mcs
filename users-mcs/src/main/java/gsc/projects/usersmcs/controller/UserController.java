package gsc.projects.usersmcs.controller;


import gsc.projects.usersmcs.dto.BuyTicketDto;
import gsc.projects.usersmcs.dto.UserCreateDto;
import gsc.projects.usersmcs.dto.UserUpdateDto;
import gsc.projects.usersmcs.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable ("userId") Long userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto){
        return new ResponseEntity<>(userService.create(userCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/buyticket")
    public ResponseEntity<?> buyTicket(@PathVariable ("userId") Long userId,@RequestBody BuyTicketDto buyTicketDto){
        return new ResponseEntity<>(userService.buyTicketByUserId(userId, buyTicketDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable ("userId") Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable ("userId") Long userId, @RequestBody UserUpdateDto userUpdateDto){
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateDto));
    }
}
