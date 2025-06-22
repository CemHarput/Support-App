package com.SupportApplication.SupportApp.Ticket.model;

import com.SupportApplication.SupportApp.Category.model.Category;
import com.SupportApplication.SupportApp.Common.BaseEntity;
import com.SupportApplication.SupportApp.Ticket.enums.TicketStatus;
import com.SupportApplication.SupportApp.User.model.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Ticket extends BaseEntity {
    private String heading;
    private String description;

    @Transient
    private TicketStatusState state = new OpenState();

    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.OPEN;


    private String answer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Ticket() {
        initializeState();
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void initializeState() {
        switch (status) {
            case OPEN -> this.state = new OpenState();
            case ANSWERED -> this.state = new AnsweredState();
            case CLOSED -> this.state = new ClosedState();
        }
    }
    public void close() {
        this.state.close(this);
    }

    public void updateStatus(TicketStatus newStatus) {
        this.status = newStatus;
        initializeState();
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.state.answer(this, answer);
    }
    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
        initializeState();
    }
    public void setRawAnswer(String answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return Objects.equals(getHeading(), ticket.getHeading()) &&
                Objects.equals(getDescription(), ticket.getDescription()) &&
                status == ticket.status &&
                Objects.equals(getCategory(), ticket.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeading(), getDescription(), status, getCategory());
    }
}
