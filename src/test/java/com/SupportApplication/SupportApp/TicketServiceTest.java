package com.SupportApplication.SupportApp;

import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Category.repository.CategoryRepository;
import com.SupportApplication.SupportApp.Ticket.dto.AddAnswerTicketDTO;
import com.SupportApplication.SupportApp.Ticket.dto.AddTicketRequestDTO;
import com.SupportApplication.SupportApp.Ticket.dto.TicketDTO;
import com.SupportApplication.SupportApp.Ticket.enums.TicketStatus;
import com.SupportApplication.SupportApp.Ticket.model.Ticket;
import com.SupportApplication.SupportApp.Ticket.repository.TicketRepository;
import com.SupportApplication.SupportApp.Ticket.service.TicketService;
import com.SupportApplication.SupportApp.User.model.User;
import com.SupportApplication.SupportApp.User.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldCreateTicketSuccessfully() {
        // Arrange
        AddTicketRequestDTO dto = new AddTicketRequestDTO("Başlık", "Açıklama", 1L);

        Category category = new Category();
        category.setName("Test Category");

        User user = new User();
        user.setUsername("testuser");

        Ticket savedTicket = new Ticket();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        Long id = ticketService.createTicket(dto, authentication);

        verify(authentication).getName();
        verify(userRepository).findByUsername("testuser");
        verify(categoryRepository).findById(1L);
        verify(ticketRepository).save(any(Ticket.class));
    }
    @Test
    void shouldAddAnswerAndUpdateStatus() {
        AddAnswerTicketDTO dto = new AddAnswerTicketDTO(100L, "Yanıt içerik");
        Ticket ticket = new Ticket();

        when(ticketRepository.findById(100L)).thenReturn(Optional.of(ticket));

        ticketService.addAnswerToTicket(dto);
        assertThat(ticket.getAnswer()).isEqualTo("Yanıt içerik");
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.ANSWERED);
        verify(ticketRepository).save(ticket);
    }
    @Test
    void shouldReturnAllTicketsAsDTOs() {

        Category category = new Category();
        category.setName("Test Category");

        Ticket ticket1 = new Ticket();
        ticket1.setHeading("Ticket 1");
        ticket1.setDescription("Desc 1");
        ticket1.setCategory(category);

        Ticket ticket2 = new Ticket();
        ticket2.setHeading("Ticket 2");
        ticket2.setDescription("Desc 2");
        ticket2.setCategory(category);

        List<Ticket> mockTickets = List.of(ticket1, ticket2);

        when(ticketRepository.findAll()).thenReturn(mockTickets);

        List<TicketDTO> result = ticketService.getAllTickets();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).heading()).isEqualTo("Ticket 1");
        assertThat(result.get(1).heading()).isEqualTo("Ticket 2");

        verify(ticketRepository).findAll();
    }
}
