function TicketForm({
  ticketTitle,
  setTicketTitle,
  ticketDescription,
  setTicketDescription,
  ticketStatus,
  setTicketStatus,
  ticketPriority,
  setTicketPriority,
  validationErrors = {},
}) {
  return (
    <div>
      <h3>Ticket Form</h3>

      <label htmlFor="ticketTitle">Ticket Title:</label>
      <input
        id="ticketTitle"
        type="text"
        value={ticketTitle}
        onChange={(e) => setTicketTitle(e.target.value)}
        aria-invalid={Boolean(validationErrors.title)}
        aria-describedby={
          validationErrors.title ? "ticketTitleError" : undefined
        }
      />

      {validationErrors.title && (
        <p id="ticketTitleError" role="alert">
          {validationErrors.title}
        </p>
      )}

      <label htmlFor="ticketDescription">Ticket Description:</label>
      <textarea
        id="ticketDescription"
        type="text"
        value={ticketDescription}
        rows={4}
        onChange={(e) => setTicketDescription(e.target.value)}
        aria-invalid={Boolean(validationErrors.description)}
        aria-describedby={
          validationErrors.description ? "ticketDescriptionError" : undefined
        }
      />

      {validationErrors.description && (
        <p id="ticketDescriptionError" role="alert">
          {validationErrors.description}
        </p>
      )}

      <label htmlFor="ticketStatus">Ticket Status:</label>
      <select
        id="ticketStatus"
        value={ticketStatus}
        onChange={(e) => setTicketStatus(e.target.value)}
        aria-invalid={Boolean(validationErrors.status)}
        aria-describedby={
          validationErrors.status ? "ticketStatusError" : undefined
        }
      >
        <option value="">Select a status</option>
        <option value="OPEN">OPEN</option>
        <option value="IN_PROGRESS">IN PROGRESS</option>
        <option value="RESOLVED">RESOLVED</option>
      </select>

      {validationErrors.status && (
        <p id="ticketStatusError" role="alert">
          {validationErrors.status}
        </p>
      )}

      <label htmlFor="ticketPriority">Ticket Priority:</label>
      <select
        id="ticketPriority"
        value={ticketPriority}
        onChange={(e) => setTicketPriority(e.target.value)}
        aria-invalid={Boolean(validationErrors.priority)}
        aria-describedby={
          validationErrors.priority ? "ticketPriorityError" : undefined
        }
      >
        <option value="">Select a priority</option>
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>

      {validationErrors.priority && (
        <p id="ticketPriorityError" role="alert">
          {validationErrors.priority}
        </p>
      )}
    </div>
  );
}

export default TicketForm;

{
  /*Inventory status drop down*/
}
{
  /* <option value="ACTIVE">ACTIVE</option>
      <option value="RETIRED">RETIRED</option>
      <option value="SHIPPING">SHIPPING</option>
      <option value="DISPOSAL">DISPOSAL</option>
      <option value="TRANSFER">TRANSFER</option>
      <option value="OUT FOR MAINTENCE">OUT FOR MAINTENCE</option>
      <option value="MAINTENCE IN PROGRESS">MAINTENCE IN PROGRESS</option> */
}
