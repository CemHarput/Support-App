package com.SupportApplication.SupportApp.Ticket.dto;

public record AddTicketRequestDTO(String heading,
                                  String description,
                                  Long categoryId) {
}
