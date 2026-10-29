package com.khadijahkelly.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Priority is required")
    private String priority;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getPriority() {
      return priority;
    }

    public void setPriority(String priority) {
      this.priority = priority;
    }
}