package com.khadijahkelly.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khadijahkelly.helpdesk.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
  
}
