package com.khadijahkelly.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.khadijahkelly.helpdesk.model.Ticket;

import java.util.List;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedById(Long userId);
    
}
