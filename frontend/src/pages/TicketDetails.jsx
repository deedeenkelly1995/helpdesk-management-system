import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getTicketById } from "../services/ticketService";

function TicketDetails() {
  const { id } = useParams();
  const [ticket, setTicket] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTicket = async () => {
      try {
        setError("");

        const data = await getTicketById(id);
        setTicket(data);
      } catch (err) {
        setError(err.message);
      }
    };
    fetchTicket();
  }, [id]);

  return (
    <div>
      <h2>Ticket Details</h2>

      {error && <p>{error}</p>}

      {ticket && (
        <div>
          {" "}
          <h3>{ticket.id}</h3>
          <h3>{ticket.title}</h3>
          <p>{ticket.description}</p>
          <p>Status: {ticket.status}</p>
          <p>Priority: {ticket.priority}</p>
          <hr />
        </div>
      )}
    </div>
  );
}

export default TicketDetails;
