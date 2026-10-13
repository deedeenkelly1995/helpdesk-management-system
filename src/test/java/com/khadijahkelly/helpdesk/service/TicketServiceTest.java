package com.khadijahkelly.helpdesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.repository.TicketRepository;
import com.khadijahkelly.helpdesk.service.TicketService;
import com.khadijahkelly.helpdesk.exception.TicketNotFoundException;

/*JUnit = testing framework
Mockito = mocking dependencies
@Mock = fake repository
@BeforeEach = runs before each test
@Test = marks a test method
when(...).thenReturn(...) = controls mock behavior
assertEquals(...) = verifies expected result*/

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

  @Mock
  private TicketRepository ticketRepository;

  private TicketService ticketService;

  @BeforeEach
  void setUp() {
    ticketService = new TicketService(ticketRepository);
  }

  // Test method to check if getAllTickets() returns all tickets
  @Test
  void shouldReturnAllTickets() {  
    Ticket ticket1 = new Ticket(
      1L,
      "Chromebook won't turn on",
      "Student device has no power",
      "OPEN",
      "HIGH"
    );

    Ticket ticket2 = new Ticket(
      2L,
      "Printer offline",
      "Office printer is not responding",
      "IN_PROGRESS",
      "MEDIUM"
    );

    when(ticketRepository.findAll()) 
      .thenReturn(List.of(ticket1, ticket2)); 
    
    List<Ticket> result = ticketService.getAllTickets(); // Call the method being tested

    assertEquals(2, result.size());   // Check that the size of the result list is 2

    assertEquals(ticket1.getId(), result.get(0).getId());     // Check that the first ticket's ID matches
  }

  // Test method to check if returnTicketById will return the ticket
  @Test   
  void shouldReturnTicketById() {     
    Ticket ticket = new Ticket(
      1L,
      "Chromebook won't turn on",
      "Student device has no power",
      "OPEN",
      "HIGH"
    );

    when(ticketRepository.findById(1L))
      .thenReturn(Optional.of(ticket));

    Ticket result = ticketService.getTicketById(1L);

    assertEquals(1L, result.getId());
    assertEquals("Chromebook won't turn on", result.getTitle());
  }

  // Test method to check if TicketNotFoundException will be thrown if no ID exist
  @Test    
  void shouldThrowExceptionWhenTicketNotFound() { 

    when(ticketRepository.findById(999L)) 
      .thenReturn(Optional.empty());

    assertThrows(
      TicketNotFoundException.class,
      () -> ticketService.getTicketById(999L)
    );
  }

  // Test method to check if ticket is created must enter for all required variables
  @Test
  void shouldCreateTicket() {
    Ticket ticket = new Ticket(
      null,
      "Project not working",
      "Classroom projector will not power on",
      "OPEN",
      "HIGH"
    );
    
    Ticket savedTicket = new Ticket(
      3L,
      "Projector not working",
      "Classroom projector will not power on",
      "OPEN",
      "HIGH"
    );
    when(ticketRepository.save(ticket)).thenReturn(savedTicket);

    Ticket result = ticketService.createTicket(ticket);

    assertEquals(3L, result.getId());
    assertEquals("Projector not working", result.getTitle());
  }

 /* Test if method to update is updating the variables by finding the ID
return error if id can not be found. Must enter for all required variables*/
@Test
void shouldUpdateTicket() {
    Ticket existingTicket = new Ticket(
        1L,
        "Old title",
        "Old description",
        "OPEN",
        "LOW"
    );

    Ticket updatedTicket = new Ticket(
        null,
        "Updated title",
        "Updated description",
        "IN_PROGRESS",
        "HIGH"
    );

    when(ticketRepository.findById(1L)).thenReturn(Optional.of(existingTicket));

    when(ticketRepository.save(existingTicket)).thenReturn(existingTicket);

    Ticket result = ticketService.updateTicket(1L, updatedTicket);

    assertEquals("Updated title", result.getTitle());
    assertEquals("Updated description", result.getDescription());
    assertEquals("IN_PROGRESS", result.getStatus());
    assertEquals("HIGH", result.getPriority());
  }

  // Test method to check if ticket is deleted
  @Test
  void shouldDeleteTicket() {
    Ticket ticket = new Ticket(
      1L,
      "Printer offline",
      "Office printer is not responding",
      "OPEN",
      "MEDIUM"
    );

    when(ticketRepository.findById(1L))
    .thenReturn(Optional.of(ticket));

    ticketService.deleteTicket(1L);

    verify(ticketRepository).delete(ticket);
  }
}