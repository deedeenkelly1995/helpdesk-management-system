package com.khadijahkelly.helpdesk.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.service.TicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; // React to access API
import com.khadijahkelly.helpdesk.dto.CreateTicketRequest;
import com.khadijahkelly.helpdesk.dto.TicketResponse;
import com.khadijahkelly.helpdesk.dto.UpdateTicketRequest;
import com.khadijahkelly.helpdesk.dto.AssignTicketRequest;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }
  // List all tickets
  @GetMapping
  public List<TicketResponse> getTickets() {
    return ticketService.getAllTickets();
  }

  // Create a new ticket
  @PostMapping
  public TicketResponse createTicket(
          @Valid @RequestBody CreateTicketRequest request) {

      return ticketService.createTicket(request);
  }


  //Get a ticket by ID
  @GetMapping("/{id}")
  public TicketResponse getTicketByID(@PathVariable Long id) {
      return ticketService.getTicketById(id);
  }

  // Update a ticket
  @PutMapping("/{id}")
  public TicketResponse updateTicket(
      @PathVariable Long id,
      @Valid @RequestBody UpdateTicketRequest request) {
    return ticketService.updateTicket(id, request);
  }

  // Delete a ticket
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }

  // Assign a technician to a ticket
  @PatchMapping("/{id}/assign")
  public TicketResponse assignTicket(
          @PathVariable Long id,
          @Valid @RequestBody AssignTicketRequest request) {

      return ticketService.assignTicket(id, request);
  }
  

    
}
