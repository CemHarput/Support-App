package com.SupportApplication.SupportApp.Ticket.service;


import com.SupportApplication.SupportApp.Category.controller.CategoryController;
import com.SupportApplication.SupportApp.Category.dto.AddCategoryRequestDTO;
import com.SupportApplication.SupportApp.Category.dto.CategoryDTO;
import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Category.repository.CategoryRepository;
import com.SupportApplication.SupportApp.Exception.EntityNotFoundException;
import com.SupportApplication.SupportApp.Ticket.dto.AddAnswerTicketDTO;
import com.SupportApplication.SupportApp.Ticket.dto.AddTicketRequestDTO;
import com.SupportApplication.SupportApp.Ticket.dto.TicketDTO;
import com.SupportApplication.SupportApp.Ticket.dto.UpdateTicketStatusDTO;
import com.SupportApplication.SupportApp.Ticket.enums.TicketStatus;
import com.SupportApplication.SupportApp.Ticket.model.Ticket;
import com.SupportApplication.SupportApp.Ticket.repository.TicketRepository;
import com.SupportApplication.SupportApp.User.model.User;
import com.SupportApplication.SupportApp.User.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;
    public TicketService(TicketRepository ticketRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    public Long createTicket(AddTicketRequestDTO addTicketRequestDTO,Authentication authentication) {
        Ticket ticket = new Ticket();
        ticket.setHeading(addTicketRequestDTO.heading());
        ticket.setDescription(addTicketRequestDTO.description());
        ticket.setCategory(resolveCategory(addTicketRequestDTO.categoryId()));

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ticket.setUser(user);
        ticketRepository.save(ticket);
        return ticket.getId();
    }

    private Category resolveCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    public void addAnswerToTicket(AddAnswerTicketDTO addAnswerTicketDTO) {
        Ticket ticket = ticketRepository.findById(addAnswerTicketDTO.ticketId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + addAnswerTicketDTO.ticketId()));

        ticket.setAnswer(addAnswerTicketDTO.answer());
        ticket.updateStatus(TicketStatus.ANSWERED);

        ticketRepository.save(ticket);
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(TicketDTO::convertFromTicket)
                .toList();
    }
    public void updateTicketState(UpdateTicketStatusDTO updateTicketStatusDTO){
        Ticket ticket = ticketRepository.findById(updateTicketStatusDTO.ticketId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + updateTicketStatusDTO.ticketId()));
        ticket.updateStatus(TicketStatus.valueOf(updateTicketStatusDTO.status()));
        ticketRepository.save(ticket);
    }
    public List<TicketDTO> getTicketsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return ticketRepository.findByUser(user)
                .stream()
                .map(TicketDTO::convertFromTicket)
                .toList();
    }
}
