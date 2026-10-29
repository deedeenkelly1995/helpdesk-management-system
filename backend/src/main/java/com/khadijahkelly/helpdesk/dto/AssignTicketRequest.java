package com.khadijahkelly.helpdesk.dto;
import jakarta.validation.constraints.NotNull;

public class AssignTicketRequest {


    @NotNull(message = "Assigned technician ID is required")
    private Long assignedToId;

    public AssignTicketRequest() {
    }

    public AssignTicketRequest(Long assignedToId) {
      this.assignedToId = assignedToId;

    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
      this.assignedToId = assignedToId;
    }

}