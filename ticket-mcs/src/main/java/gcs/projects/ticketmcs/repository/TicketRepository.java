package gcs.projects.ticketmcs.repository;

import gcs.projects.ticketmcs.model.Ticket;
import gcs.projects.ticketmcs.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventCode(String eventCode);
    List<Ticket> findByEventCodeAndStatus(String eventCode, TicketStatus ticketStatus);
}
