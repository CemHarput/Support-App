package com.SupportApplication.SupportApp.Ticket.model;

public interface TicketStatusState {
    void answer(Ticket ticket, String answer);
    void close(Ticket ticket);
}
