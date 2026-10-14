import { useEffect, useState } from "react";
import { getAllTickets } from "../services/ticketService";

function TicketList() {
  const [tickets, setTickets] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        setError("");

        const data = await getAllTickets();
        setTickets(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchTickets();
  }, []);

  return (
    <div>
      <h2>Helpdesk Tickets</h2>

      {error && <p>{error}</p>}

      {tickets.length === 0 && !error && <p>No tickets found.</p>}

      {tickets.map((ticket) => (
        <div key={ticket.id}>
          <h3>{ticket.title}</h3>
          <p>{ticket.description}</p>
          <p>Status: {ticket.status}</p>
          <p>Priority: {ticket.priority}</p>
          <hr />
        </div>
      ))}
    </div>
  );
}

export default TicketList;
