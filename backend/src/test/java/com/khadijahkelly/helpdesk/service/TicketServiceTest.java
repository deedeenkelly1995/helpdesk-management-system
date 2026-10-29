
package com.khadijahkelly.helpdesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.khadijahkelly.helpdesk.dto.CreateTicketRequest;
import com.khadijahkelly.helpdesk.dto.TicketResponse;
import com.khadijahkelly.helpdesk.exception.TicketNotFoundException;
import com.khadijahkelly.helpdesk.model.Ticket;
import com.khadijahkelly.helpdesk.model.User;
import com.khadijahkelly.helpdesk.repository.TicketRepository;
import com.khadijahkelly.helpdesk.repository.UserRepository;
import com.khadijahkelly.helpdesk.model.Role;
import static org.mockito.Mockito.never;
import org.springframework.security.access.AccessDeniedException;
import com.khadijahkelly.helpdesk.dto.UpdateTicketRequest;
import com.khadijahkelly.helpdesk.dto.AssignTicketRequest;
import com.khadijahkelly.helpdesk.exception.TechnicianNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketService(
            ticketRepository,
            userRepository
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // Test retrieving all tickets admin test
    @Test
    void shouldReturnAllTickets() {

        User admin = mock(User.class);

        when(admin.getRole()).thenReturn(Role.ADMIN);

        when(userRepository.findByEmail("admin@example.com"))
            .thenReturn(Optional.of(admin));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "admin@example.com",
                null
            )
        );

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

        List<TicketResponse> result =
            ticketService.getAllTickets();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(ticketRepository).findAll();
    }

    // Return all tickets User Test
    @Test
    void shouldReturnOnlyUsersTickets() {

        User user = mock(User.class);

        when(user.getRole()).thenReturn(Role.USER);
        when(user.getId()).thenReturn(2L);

        when(userRepository.findByEmail("user@example.com"))
            .thenReturn(Optional.of(user));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "user@example.com",
                null
            )
        );

        Ticket ticket = new Ticket(
            3L,
            "Printer not working",
            "Office printer is offline",
            "OPEN",
            "HIGH"
        );

        ticket.setCreatedBy(user);

        // Next: Mock the tickets belonging to this user
        when(ticketRepository.findByCreatedById(2L))
            .thenReturn(List.of(ticket));

        List<TicketResponse> result = ticketService.getAllTickets();

        assertEquals(1, result.size());
        assertEquals(3L, result.get(0).getId());
        assertEquals(2L, result.get(0).getCreatedById());
        
        verify(ticketRepository, never()).findAll();
        verify(ticketRepository).findByCreatedById(2L);
    }

    // Technician Test retrieve all tickets
    @Test
    void shouldReturnAllTicketsForTechnician() {

        User technician = mock(User.class);

        when(technician.getRole())
            .thenReturn(Role.TECHNICIAN);

        when(userRepository.findByEmail("tech@example.com"))
            .thenReturn(Optional.of(technician));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "tech@example.com",
                null
            )
        );

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

        List<TicketResponse> result =
            ticketService.getAllTickets();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(ticketRepository).findAll();
        verify(ticketRepository, never()).findByCreatedById(any());
    }



    // Test retrieving a ticket by ID for a user
    @Test
    void shouldReturnTicketById() {

        User user = mock(User.class);

        when(user.getId()).thenReturn(2L);
        when(user.getRole()).thenReturn(Role.USER);

        when(userRepository.findByEmail("user@example.com"))
            .thenReturn(Optional.of(user));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "user@example.com",
                null
            )
        );

        Ticket ticket = new Ticket(
            1L,
            "Chromebook won't turn on",
            "Student device has no power",
            "OPEN",
            "HIGH"
        );

        ticket.setCreatedBy(user);

        when(ticketRepository.findById(1L))
            .thenReturn(Optional.of(ticket));

        TicketResponse result = ticketService.getTicketById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Chromebook won't turn on", result.getTitle());
        assertEquals(2L, result.getCreatedById());
    }


    // Test retrieving a nonexistent ticket
    @Test
    void shouldThrowExceptionWhenTicketNotFound() {

        User user = mock(User.class);

        when(userRepository.findByEmail("user@example.com"))
            .thenReturn(Optional.of(user));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "user@example.com",
                null
            )
        );

        when(ticketRepository.findById(999L))
            .thenReturn(Optional.empty());

        assertThrows(
            TicketNotFoundException.class,
            () -> ticketService.getTicketById(999L)
        );
    }

    // Test creating a ticket for the authenticated user
    @Test
    void shouldCreateTicket() {

        // Mock the authenticated user
        User user = mock(User.class);

        when(user.getId()).thenReturn(2L);

        when(userRepository.findByEmail("user@example.com"))
            .thenReturn(Optional.of(user));

        // Simulate authentication
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "user@example.com",
                null
            )
        );

        // Create the request DTO
        CreateTicketRequest request = new CreateTicketRequest();

        request.setTitle("Projector not working");
        request.setDescription(
            "Classroom projector will not power on"
        );
        request.setPriority("HIGH");

        // Simulate a saved ticket
        Ticket savedTicket = new Ticket(
            3L,
            "Projector not working",
            "Classroom projector will not power on",
            "OPEN",
            "HIGH"
        );

        savedTicket.setCreatedBy(user);

        when(ticketRepository.save(any(Ticket.class)))
            .thenReturn(savedTicket);

        // Execute the service method
        TicketResponse result = ticketService.createTicket(request);

        // Verify the response
        assertEquals(3L, result.getId());
        assertEquals("Projector not working", result.getTitle());
        assertEquals(
            "Classroom projector will not power on",
            result.getDescription()
        );
        assertEquals("OPEN", result.getStatus());
        assertEquals("HIGH", result.getPriority());
        assertEquals(2L, result.getCreatedById());
        assertEquals(null, result.getAssignedToId());

        // Capture the ticket actually passed to save()
        ArgumentCaptor<Ticket> ticketCaptor =
            ArgumentCaptor.forClass(Ticket.class);

        verify(ticketRepository).save(ticketCaptor.capture());

        Ticket capturedTicket = ticketCaptor.getValue();

        // Verify the backend assigned the correct values
        assertEquals("OPEN", capturedTicket.getStatus());
        assertEquals(2L, capturedTicket.getCreatedBy().getId());
        assertEquals(
            "Projector not working",
            capturedTicket.getTitle()
        );
        assertEquals(
            "Classroom projector will not power on",
            capturedTicket.getDescription()
        );
        assertEquals("HIGH", capturedTicket.getPriority());
        assertEquals(null, capturedTicket.getAssignedTo());
    }

    // Test updating an existing ticket
    @Test
    void shouldUpdateTicket() {

        Ticket existingTicket = new Ticket(
            1L,
            "Old title",
            "Old description",
            "OPEN",
            "LOW"
        );

        UpdateTicketRequest request = new UpdateTicketRequest();

        request.setTitle("Updated title");
        request.setDescription("Updated description");
        request.setStatus("IN_PROGRESS");
        request.setPriority("HIGH");

        when(ticketRepository.findById(1L))
            .thenReturn(Optional.of(existingTicket));

        when(ticketRepository.save(existingTicket))
            .thenReturn(existingTicket);

        TicketResponse result = ticketService.updateTicket(1L, request);

        assertEquals("Updated title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertEquals("IN_PROGRESS", result.getStatus());
        assertEquals("HIGH", result.getPriority());
    }

    // Test updating a nonexistent ticket
    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentTicket() {

        UpdateTicketRequest request = new UpdateTicketRequest();

        request.setTitle("Updated title");
        request.setDescription("Updated description");
        request.setStatus("IN_PROGRESS");
        request.setPriority("HIGH");

        when(ticketRepository.findById(999L))
            .thenReturn(Optional.empty());

        assertThrows(
            TicketNotFoundException.class,
            () -> ticketService.updateTicket(999L, request)
        );
    }

    // Test deleting an existing ticket
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

    // Test deleting a nonexistent ticket
    @Test
    void shouldThrowExceptionWhenDeletingNonexistentTicket() {

        when(ticketRepository.findById(999L))
            .thenReturn(Optional.empty());

        assertThrows(
            TicketNotFoundException.class,
            () -> ticketService.deleteTicket(999L)
        );
    }


    @Test
    void shouldDenyAccessToAnotherUsersTicket() {

        User currentUser = mock(User.class);
        User ticketOwner = mock(User.class);

        when(currentUser.getRole()).thenReturn(Role.USER);
        when(currentUser.getId()).thenReturn(2L);
        when(ticketOwner.getId()).thenReturn(5L);

        when(userRepository.findByEmail("user@example.com"))
            .thenReturn(Optional.of(currentUser));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "user@example.com",
                null
            )
        );

        Ticket ticket = new Ticket(
            1L,
            "Printer not working",
            "Office printer is offline",
            "OPEN",
            "HIGH"
        );

        ticket.setCreatedBy(ticketOwner);

        when(ticketRepository.findById(1L))
            .thenReturn(Optional.of(ticket));

        assertThrows(
            AccessDeniedException.class,
            () -> ticketService.getTicketById(1L)
        );
    }

    // Test if technician is assigned to the ticket
    @Test
    void shouldAssignTicketToTechnician() {

        User currentUser = mock(User.class);
        User technician = mock(User.class);
        User ticketOwner = mock(User.class);

        

        when(currentUser.getRole()).thenReturn(Role.ADMIN);

        when(userRepository.findByEmail("admin@example.com"))
            .thenReturn(Optional.of(currentUser));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "admin@example.com",
                null
            )
        );

        when(technician.getRole()).thenReturn(Role.TECHNICIAN);
        when(technician.getId()).thenReturn(3L);

        when(userRepository.findById(3L))
            .thenReturn(Optional.of(technician));

        Ticket ticket = new Ticket(
            5L,
            "Printer offline",
            "Office printer is not responding",
            "OPEN",
            "MEDIUM"
        );
       
        AssignTicketRequest request = new AssignTicketRequest(3L);

        when(ticketOwner.getId()).thenReturn(2L);

        ticket.setCreatedBy(ticketOwner);

        when(ticketRepository.findById(5L))
            .thenReturn(Optional.of(ticket));

        when(ticketRepository.save(ticket))
            .thenReturn(ticket);

        TicketResponse result = ticketService.assignTicket(5L, request);

        ArgumentCaptor<Ticket> ticketCaptor =
            ArgumentCaptor.forClass(Ticket.class);

        verify(ticketRepository).save(ticketCaptor.capture());

        Ticket capturedTicket = ticketCaptor.getValue();

        assertEquals(2L, capturedTicket.getCreatedBy().getId());
        assertEquals(2L, result.getCreatedById());
        assertEquals("Printer offline", capturedTicket.getTitle());
        assertEquals("Office printer is not responding", capturedTicket.getDescription());
        assertEquals(5L, capturedTicket.getId());
        assertEquals(3L, capturedTicket.getAssignedTo().getId());
        assertEquals(3L, result.getAssignedToId());

    }

    @Test
    void shouldDenyTicketAssignmentForRegularUser() {

        User currentUser = mock(User.class);

        when(currentUser.getRole()).thenReturn(Role.USER);

        when(userRepository.findByEmail("user@example.com"))
            .thenReturn(Optional.of(currentUser));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "user@example.com",
                null
            )
        );

        AssignTicketRequest request = new AssignTicketRequest(3L);

        // Add your assertions here
        assertThrows(
            AccessDeniedException.class,
            () -> ticketService.assignTicket(5L, request)
        );

        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void shouldRejectAssignmentToRegularUser() {

        User currentUser = mock(User.class);
        User assignedUser = mock(User.class);

        when(currentUser.getRole()).thenReturn(Role.ADMIN);

        when(userRepository.findByEmail("admin@example.com"))
            .thenReturn(Optional.of(currentUser));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "admin@example.com",
                null
            )
        );

        when(assignedUser.getRole()).thenReturn(Role.USER);

        when(userRepository.findById(3L))
            .thenReturn(Optional.of(assignedUser));

        AssignTicketRequest request = new AssignTicketRequest(3L);

        assertThrows(
            IllegalArgumentException.class,
            () -> ticketService.assignTicket(5L, request)
        );

        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void shouldThrowExceptionWhenTechnicianNotFound() {

        User currentUser = mock(User.class);

        when(currentUser.getRole()).thenReturn(Role.ADMIN);

        when(userRepository.findByEmail("admin@example.com"))
            .thenReturn(Optional.of(currentUser));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "admin@example.com",
                null
            )
        );

        AssignTicketRequest request = new AssignTicketRequest(999L);

        when(userRepository.findById(999L))
            .thenReturn(Optional.empty());

        assertThrows(
            TechnicianNotFoundException.class,
            () -> ticketService.assignTicket(5L, request)
        );

        verify(ticketRepository, never()).save(any(Ticket.class));
    }

}
