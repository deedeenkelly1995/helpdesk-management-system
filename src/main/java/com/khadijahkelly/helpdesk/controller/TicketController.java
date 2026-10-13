package com.khadijahkelly.helpdesk.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.service.TicketService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }
  // List all tickets
  @GetMapping
  public List<Ticket> getTickets() {
    return ticketService.getAllTickets();
  }

// Create a new ticket
  @PostMapping
  public Ticket createTicket(@Valid @RequestBody Ticket ticket) {
    return ticketService.createTicket(ticket);
  }

  //Get a ticket by ID
  @GetMapping("/{id}")
  public Ticket getTicketByID(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }

  // Update a ticket
  @PutMapping("/{id}")
  public Ticket updateTicket(
    @PathVariable Long id,
    @Valid @RequestBody Ticket ticket) {
      return ticketService.updateTicket(id, ticket);
    }

    // Delete a ticket
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
      ticketService.deleteTicket(id);
    }

    
}
