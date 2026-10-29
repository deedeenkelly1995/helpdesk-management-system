package com.khadijahkelly.helpdesk.service;

import java.util.Objects;
import java.util.List;
import org.springframework.stereotype.Service;
import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.repository.TicketRepository;
import com.khadijahkelly.helpdesk.exception.TicketNotFoundException;
import com.khadijahkelly.helpdesk.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import com.khadijahkelly.helpdesk.model.User;
import com.khadijahkelly.helpdesk.dto.CreateTicketRequest;
import com.khadijahkelly.helpdesk.dto.TicketResponse;
import com.khadijahkelly.helpdesk.model.Role;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import com.khadijahkelly.helpdesk.dto.UpdateTicketRequest;
import com.khadijahkelly.helpdesk.dto.AssignTicketRequest;
import com.khadijahkelly.helpdesk.exception.TechnicianNotFoundException;

@Service 
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                        UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }
    
    // List all tickets
    public List<TicketResponse> getAllTickets() {
        User currentUser = getCurrentUser();
        List<Ticket> tickets;

        if (currentUser.getRole() == Role.ADMIN ||
            currentUser.getRole() == Role.TECHNICIAN) {

            tickets = ticketRepository.findAll();

        } else {

            tickets = ticketRepository.findByCreatedById(currentUser.getId());
        }
        
        return tickets.stream()
            .map(this::toTicketResponse)
            .collect(Collectors.toList());
    }

    // Create a new ticket
    public TicketResponse createTicket(CreateTicketRequest request) {

        Ticket ticket = new Ticket();
        User currentUser = getCurrentUser();

        ticket.setCreatedBy(currentUser);
        ticket.setStatus("OPEN");
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        Ticket savedTicket = ticketRepository.save(ticket);
        return toTicketResponse(savedTicket);
    }

    // Get a ticket by ID
    public TicketResponse getTicketById(Long id) {

        User currentUser = getCurrentUser();

        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));

        if (currentUser.getRole() == Role.USER &&
            (ticket.getCreatedBy() == null ||
            !Objects.equals(
                currentUser.getId(),
                ticket.getCreatedBy().getId()
            ))) {

            throw new AccessDeniedException(
                "You do not have permission to view this ticket"
            );
        }

        return toTicketResponse(ticket);
    }       

    // Update a ticket
    public TicketResponse updateTicket(Long id, UpdateTicketRequest request) {

    Ticket existingTicket = ticketRepository.findById(id)
        .orElseThrow(() -> new TicketNotFoundException(id));

    existingTicket.setTitle(request.getTitle());
    existingTicket.setDescription(request.getDescription());
    existingTicket.setStatus(request.getStatus());
    existingTicket.setPriority(request.getPriority());

    Ticket savedTicket = ticketRepository.save(existingTicket);

    return toTicketResponse(savedTicket);
}

    // Delete a ticket
    public void deleteTicket(Long id) {
    Ticket existingTicket = ticketRepository.findById(id)
        .orElseThrow(() -> new TicketNotFoundException(id));

    ticketRepository.delete(existingTicket);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

        // Next: Find the user in PostgreSQL
        return userRepository.findByEmail(email)
        .orElseThrow(() ->
            new IllegalStateException(
                "Authenticated user not found"
            )
        );
    }

    private TicketResponse toTicketResponse(Ticket ticket) {

        return new TicketResponse(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getStatus(),
            ticket.getPriority(),
            ticket.getCreatedBy() != null
                ? ticket.getCreatedBy().getId()
                : null,
            ticket.getAssignedTo() != null
                ? ticket.getAssignedTo().getId()
                : null
        );
    }

    // Assign a technician to a ticket
    public TicketResponse assignTicket(Long ticketId, AssignTicketRequest request) {

        User currentUser = getCurrentUser();

        if (currentUser.getRole() != Role.TECHNICIAN
                && currentUser.getRole() != Role.ADMIN) {

            throw new AccessDeniedException(
                "You do not have permission to assign tickets"
            );
        }

        User technician = userRepository.findById(request.getAssignedToId())
            .orElseThrow(() -> new TechnicianNotFoundException(request.getAssignedToId()));

        if (technician.getRole() != Role.TECHNICIAN) {
            throw new IllegalArgumentException(
                "Tickets can only be assigned to technicians"
            );
        }

        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));

        ticket.setAssignedTo(technician);

        Ticket savedTicket = ticketRepository.save(ticket);

        return toTicketResponse(savedTicket);
    }
    
}