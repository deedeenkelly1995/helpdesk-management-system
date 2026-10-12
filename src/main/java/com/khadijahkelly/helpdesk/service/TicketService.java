package com.khadijahkelly.helpdesk.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.repository.TicketRepository;

@Service 

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
    }
    // List all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    // Create a new ticket
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}