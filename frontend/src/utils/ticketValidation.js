export function validateTicket(ticket) {
  const errors = {};

  if (ticket.title.trim() === "") {
    errors.title = "Title is required.";
  }

  if (ticket.description.trim() === "") {
    errors.description = "Description is required.";
  }

  if (
    ticket.status !== "OPEN" &&
    ticket.status !== "IN_PROGRESS" &&
    ticket.status !== "RESOLVED"
  ) {
    errors.status = "Status is required.";
  }

  if (
    ticket.priority !== "LOW" &&
    ticket.priority !== "MEDIUM" &&
    ticket.priority !== "HIGH"
  ) {
    errors.priority = "Priority is required.";
  }

  return errors;
}
