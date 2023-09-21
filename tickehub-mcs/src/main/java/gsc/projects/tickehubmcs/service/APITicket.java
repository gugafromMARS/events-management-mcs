package gsc.projects.tickehubmcs.service;


import gsc.projects.tickehubmcs.dto.TicketDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8083", value = "TICKET-SERVICE")
public interface APITicket {

    @GetMapping("/tickets/{eventCode}")
    TicketDto getTicket(@PathVariable("eventCode") String eventCode);
}
