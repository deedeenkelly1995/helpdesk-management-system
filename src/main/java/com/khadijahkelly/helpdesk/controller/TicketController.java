package com.khadijahkelly.helpdesk.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.service.TicketService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
// Create a new ticket
  @PostMapping
  public Ticket createTicket(@RequestBody Ticket ticket) {
    return ticketService.createTicket(ticket);
  }
