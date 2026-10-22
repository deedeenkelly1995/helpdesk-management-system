import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getTicketById, deleteTicket } from "../services/ticketService";

function TicketDetails() {
  const { id } = useParams();
  const [ticket, setTicket] = useState(null);
  const [error, setError] = useState("");
  const [deleteError, setDeleteError] = useState("");
  const navigate = useNavigate();

  //Loading
  const [loading, setLoading] = useState(true);

  //Deleting
  const [deleting, setDeleting] = useState(false);

  useEffect(() => {
    const fetchTicket = async () => {
      setLoading(true);
      setTicket(null);
      try {
        setError("");

        const data = await getTicketById(id);
        setTicket(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
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
      setDeleting(true);
      setDeleteError("");

      await deleteTicket(id);
      navigate("/tickets");
    } catch (err) {
      console.error("Failed to delete ticket:", err);
      setDeleteError("Failed to delete ticket. Please try again.");
    } finally {
      setDeleting(false);
    }
  };

  return (
    <div>
      <h2>Ticket Details</h2>

      {loading && <p>Loading ticket...</p>}

      {error && <p>{error}</p>}

      {deleteError && <p>{deleteError}</p>}

      {!loading && !error && ticket && (
        <div>
          {" "}
          <h3>{ticket.id}</h3>
          <h3>{ticket.title}</h3>
          <p>{ticket.description}</p>
          <p>Status: {ticket.status}</p>
          <p>Priority: {ticket.priority}</p>
          <hr />
          <button
            type="button"
            disabled={deleting}
            onClick={() => {
              navigate(`/tickets/${id}/edit`);
            }}
          >
            Edit Ticket
          </button>
          <button type="button" disabled={deleting} onClick={handleDelete}>
            {deleting ? "Deleting..." : " Delete Ticket"}
          </button>
        </div>
      )}
    </div>
  );
}

export default TicketDetails;
