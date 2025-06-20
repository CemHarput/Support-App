package com.SupportApplication.SupportApp.Ticket.model;

public class ClosedState implements TicketStatusState{
    @Override
    public void answer(Ticket ticket, String answer) {
        throw new UnsupportedOperationException("Ticket is closed and can't be answered.");
    }

    @Override
    public void close(Ticket ticket) {
        // already closed
    }
}
