package com.SupportApplication.SupportApp.Ticket.model;

import com.SupportApplication.SupportApp.Ticket.enums.TicketStatus;

public class OpenState implements TicketStatusState {
    @Override
    public void answer(Ticket ticket, String answer) {
        ticket.setRawAnswer(answer);
        ticket.updateStatus(TicketStatus.ANSWERED);
    }

    @Override
    public void close(Ticket ticket) {
        throw new UnsupportedOperationException("Open ticket can't be closed directly!");
    }
}
