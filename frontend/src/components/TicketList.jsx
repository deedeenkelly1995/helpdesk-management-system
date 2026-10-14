import { useEffect, useState } from "react";

function TicketList() {
  const [tickets, setTickets] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/api/tickets")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch tickets");
        }
        return response.json();
      })
      .then((data) => setTickets(data))
      .catch((err) => setError(err.message));
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
