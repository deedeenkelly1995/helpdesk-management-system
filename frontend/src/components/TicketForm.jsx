function TicketForm({
  ticketTitle,
  setTicketTitle,
  ticketDescription,
  setTicketDescription,
  ticketStatus,
  setTicketStatus,
  ticketPriority,
  setTicketPriority,
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
        required
      />

      <label htmlFor="ticketDescription">Ticket Description:</label>
      <input
        id="ticketDescription"
        type="text"
        value={ticketDescription}
        onChange={(e) => setTicketDescription(e.target.value)}
        required
      />

      <label htmlFor="ticketStatus">Ticket Status:</label>
      <select
        id="ticketStatus"
        value={ticketStatus}
        onChange={(e) => setTicketStatus(e.target.value)}
        required
      >
        <option value="">Select a status</option>
        <option value="OPEN">OPEN</option>
        <option value="IN_PROGRESS">IN PROGRESS</option>
        <option value="RESOLVED">RESOLVED</option>
      </select>

      <label htmlFor="ticketPriority">Ticket Priority:</label>
      <select
        id="ticketPriority"
        value={ticketPriority}
        onChange={(e) => setTicketPriority(e.target.value)}
        required
      >
        <option value="">Select a priority</option>
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>
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
