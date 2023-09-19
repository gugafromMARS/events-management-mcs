package gcs.projects.ticketmcs.repository;

import gcs.projects.ticketmcs.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByEventCode(String eventCode);
    List<Ticket> findAllByEventCode(String eventCode);
}
