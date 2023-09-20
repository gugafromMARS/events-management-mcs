package gcs.projects.ticketmcs.service;

import gcs.projects.ticketmcs.dto.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "http://localhost:8081", value ="EVENT-SERVICE")
public interface APIClient {

    @GetMapping("/events/{eventCode}")
    EventDto getAnEventByCode(@PathVariable("eventCode") String eventCode);
}
