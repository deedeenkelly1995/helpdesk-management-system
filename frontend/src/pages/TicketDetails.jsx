import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getTicketById, deleteTicket } from "../services/ticketService";

function TicketDetails() {
  const { id } = useParams();
  const [ticket, setTicket] = useState(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();

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

  const handleDelete = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this ticket?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await deleteTicket(id);
      navigate("/tickets");
    } catch (error) {
      console.error("Failed to delete ticket:", error);
    }
  };

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
          <button type="button" onClick={handleDelete}>
            Delete Ticket
          </button>
        </div>
      )}
    </div>
  );
}

export default TicketDetails;
