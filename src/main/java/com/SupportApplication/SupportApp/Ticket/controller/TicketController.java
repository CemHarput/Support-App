package com.SupportApplication.SupportApp.Ticket.controller;

import com.SupportApplication.SupportApp.Common.DTO.ApiResponse;
import com.SupportApplication.SupportApp.Ticket.dto.AddAnswerTicketDTO;
import com.SupportApplication.SupportApp.Ticket.dto.AddTicketRequestDTO;
import com.SupportApplication.SupportApp.Ticket.dto.TicketDTO;
import com.SupportApplication.SupportApp.Ticket.dto.UpdateTicketStatusDTO;
import com.SupportApplication.SupportApp.Ticket.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Ticket Controller", description = "Ticket management endpoints")
@Slf4j
@RequestMapping("/api/v1/")
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @PostMapping("/tickets")
    public ResponseEntity<ApiResponse> createTicket(@RequestBody AddTicketRequestDTO addTicketRequestDTO,Authentication authentication) {
        try {
            logger.debug("createTicket is started");

            Long ticketId = ticketService.createTicket(addTicketRequestDTO,authentication);
            logger.info("Ticket created successfully with ID: {}", ticketId);

            ApiResponse response = new ApiResponse("Ticket created with ID: " + ticketId, HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating ticket: {}", e.getMessage(), e);
            ApiResponse error = new ApiResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/tickets/answer")
    public ResponseEntity<ApiResponse> addAnswer(
            @RequestBody AddAnswerTicketDTO addAnswerTicketDTO) {
        ticketService.addAnswerToTicket(addAnswerTicketDTO);
        return ResponseEntity.ok(new ApiResponse("Answer added and status updated", 200));
    }
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> ticketList = ticketService.getAllTickets();
        return ResponseEntity.ok(ticketList);
    }
    @PatchMapping("/tickets/status")
    public ResponseEntity<ApiResponse> updateTicketStatus(@RequestBody UpdateTicketStatusDTO updateTicketStatusDTO){
        ticketService.updateTicketState(updateTicketStatusDTO);
        return ResponseEntity.ok(new ApiResponse("Ticket status updated",200));

    }
    @GetMapping("/tickets/user")
    public List<TicketDTO> getTicketsForLoggedInUser(Authentication authentication) {
        return ticketService.getTicketsForUser(authentication.getName());
    }

}
