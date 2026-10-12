package com.khadijahkelly.helpdesk.controller;

import com.khadijahkelly.helpdesk.HelpdeskApplication;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.service.TicketService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  private final HelpdeskApplication helpdeskApplication;
  private final TicketService ticketService;

  public TicketController(TicketService ticketService, HelpdeskApplication helpdeskApplication) {
    this.ticketService = ticketService;
    this.helpdeskApplication = helpdeskApplication;
  }
  // List all tickets
  @GetMapping
  public List<Ticket> getTickets() {
    return ticketService.getAllTickets();
  }

// Create a new ticket
  @PostMapping
  public Ticket createTicket(@RequestBody Ticket ticket) {
    return ticketService.createTicket(ticket);
  }

  //Get a ticket by ID
  @GetMapping("/{id}")
  public Ticket getTicketByID(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }

}
