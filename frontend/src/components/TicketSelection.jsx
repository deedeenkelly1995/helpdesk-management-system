import { useNavigate } from "react-router-dom";

function TicketSelection({ tickets, selectedTicketId, setSelectedTicketId }) {
  const navigate = useNavigate();

  const editTicket = (ticketId) => {
    navigate(`/tickets/${ticketId}/edit`);
  };

  return (
    <div>
      <select
        value={selectedTicketId}
        onChange={(e) => setSelectedTicketId(e.target.value)}
      >
        <option value="">Edit Selected Ticket</option>
        {tickets.map((ticket) => (
          <option key={ticket.id} value={ticket.id}>
            {ticket.title}
          </option>
        ))}
      </select>
      <button
        disabled={!selectedTicketId}
        onClick={() => editTicket(selectedTicketId)}
      >
        Edit Selected Ticket
      </button>
    </div>
  );
}
export default TicketSelection;
