package gsc.projects.eventsmcs.repository;

import gsc.projects.eventsmcs.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

     Event findByNameAndLocalDate(String name, LocalDate localDate);
     Event findByEventCode(String eventCode);
}
