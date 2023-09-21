package gsc.projects.tickehubmcs.repository;

import gsc.projects.tickehubmcs.dto.TicketDto;
import gsc.projects.tickehubmcs.model.UserTickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface UserTicketsRepository extends JpaRepository<UserTickets, Long> {

    List<UserTickets> findAllByEventCodeAndUserId(String eventCode, Long userId);
}
