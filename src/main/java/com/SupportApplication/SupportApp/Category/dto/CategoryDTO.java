package com.SupportApplication.SupportApp.Category.dto;

import com.SupportApplication.SupportApp.Category.model.Category;

public record CategoryDTO(Long id, String name, int ticketCount) {
    public static CategoryDTO convertFromCategory(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getTickets() == null ? 0 : category.getTickets().size()
        );
    }
}
