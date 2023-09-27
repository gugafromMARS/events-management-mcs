package gsc.projects.tickehubmcs.service;


import gsc.projects.tickehubmcs.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERS-SERVICE")
public interface APIUser {

    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable("userId") Long userId);
}
