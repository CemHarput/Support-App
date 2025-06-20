package com.SupportApplication.SupportApp.Ticket.repository;

import com.SupportApplication.SupportApp.Ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
