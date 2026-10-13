package com.khadijahkelly.helpdesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Changelog.Timestamp;
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

  @Test
  void shouldReturnAllTickets() {  // Test method to check if getAllTickets() returns all tickets
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

  @Test   
  void shouldReturnTicketById() {     // Test method to check if returnTicketById will return the ticket
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

  @Test    
  void shouldThrowExceptionWhenTicketNotFound() { // Test method to check if TicketNotFoundException will be thrown if no ID exist

    when(ticketRepository.findById(999L)) 
      .thenReturn(Optional.empty());

    assertThrows(
      TicketNotFoundException.class,
      () -> ticketService.getTicketById(999L)
    );
  }

}