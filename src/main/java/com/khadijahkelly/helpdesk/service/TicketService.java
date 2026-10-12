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

    // Get a ticket by ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }

    // Update a ticket
    public Ticket updateTicket(Long id, Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id)
        );
        existingTicket.setTitle(updatedTicket.getTitle());
        existingTicket.setDescription(updatedTicket.getDescription());
        existingTicket.setStatus(updatedTicket.getStatus());
        existingTicket.setPriority(updatedTicket.getPriority());

        return ticketRepository.save(existingTicket);
    }
}