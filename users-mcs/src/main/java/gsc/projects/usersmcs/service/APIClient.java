package gsc.projects.usersmcs.service;


import gsc.projects.usersmcs.dto.UserTicketsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8082", value = "TICKETHUB-SERVICE")
public interface APIClient {

    @GetMapping ("/tickethub/{ticketHubId}/buy/{eventCode}/{userId}")
    UserTicketsDto buy(@PathVariable("ticketHubId") Long ticketHubId,
                       @PathVariable ("eventCode") String eventCode,
                       @PathVariable ("userId") Long userId);
}
