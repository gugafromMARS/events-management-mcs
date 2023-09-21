package gsc.projects.tickehubmcs.service;


import gsc.projects.tickehubmcs.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080/users", value = "USERS-SERVICE")
public interface APIUser {

    @GetMapping("/{userId}")
    UserDto getUserById(@PathVariable("userId") Long userId);
}
