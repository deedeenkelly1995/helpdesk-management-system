package com.khadijahkelly.helpdesk.dto;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long createdById;
    private Long assignedToId;

    public TicketResponse(
      Long id,
      String title,
      String description,
      String status,
      String priority,
      Long createdById,
      Long assignedToId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdById = createdById;
        this.assignedToId = assignedToId;
      }
      public Long getId() {
        return id;
      }
      public String getTitle() {
        return title;
      }
      public String getDescription() {
        return description;
      }
      public String getStatus() {
        return status;
      }
      public String getPriority() {
        return priority;
      }
      public Long getCreatedById() {
        return createdById;
      }
      public Long getAssignedToId() {
        return assignedToId;
      }

}