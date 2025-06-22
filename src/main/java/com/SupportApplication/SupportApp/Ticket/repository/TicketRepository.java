package com.SupportApplication.SupportApp.Ticket.repository;

import com.SupportApplication.SupportApp.Ticket.model.Ticket;
import com.SupportApplication.SupportApp.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findByUser(User user);
}
