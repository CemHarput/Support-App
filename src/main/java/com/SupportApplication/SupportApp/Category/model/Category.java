package com.SupportApplication.SupportApp.Category.model;


import com.SupportApplication.SupportApp.Common.BaseEntity;
import com.SupportApplication.SupportApp.Ticket.model.Ticket;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity
public class Category extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)//PERSIST YAP
    private List<Ticket> tickets;
    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
