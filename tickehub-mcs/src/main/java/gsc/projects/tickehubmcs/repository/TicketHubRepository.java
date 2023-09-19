package gsc.projects.tickehubmcs.repository;

import gsc.projects.tickehubmcs.model.TicketHub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketHubRepository extends JpaRepository<TicketHub, Long> {


    TicketHub findByEmail(String email);
}
