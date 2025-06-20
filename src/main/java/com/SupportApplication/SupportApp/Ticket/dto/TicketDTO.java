package com.SupportApplication.SupportApp.Ticket.dto;

import com.SupportApplication.SupportApp.Ticket.model.Ticket;

public record TicketDTO(
        Long id,
        String heading,
        String description,
        String status,
        String categoryName,
        String answer
) {
    public static TicketDTO convertFromTicket(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getHeading(),
                ticket.getDescription(),
                ticket.getStatus().toString(),
                ticket.getCategory().getName(),
                ticket.getAnswer()
        );
    }
}

