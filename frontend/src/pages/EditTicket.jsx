import { useEffect, useState } from "react";
import { getTicketById, updateTicket } from "../services/ticketService";
import { useParams, useNavigate } from "react-router-dom";
import TicketForm from "../components/TicketForm";
import { validateTicket } from "../utils/ticketValidation";

function EditTicket() {
  const { id } = useParams();
  const [error, setError] = useState("");
  const [ticketTitle, setTicketTitle] = useState("");
  const [ticketDescription, setTicketDescription] = useState("");
  const [ticketStatus, setTicketStatus] = useState("");
  const [ticketPriority, setTicketPriority] = useState("");
  const [isSaving, setIsSaving] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saveError, setSaveError] = useState("");
  const navigate = useNavigate();
  const [validationErrors, setValidationErrors] = useState({});

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedTicket = {
      title: ticketTitle,
      description: ticketDescription,
      status: ticketStatus,
      priority: ticketPriority,
    };

    const errors = validateTicket(updatedTicket);
    setValidationErrors(errors);
    if (Object.keys(errors).length > 0) {
      return;
    }

    try {
      setIsSaving(true);
      setSaveError("");

      await updateTicket(id, updatedTicket);
      console.log("Ticket updated");
      navigate(`/tickets/${id}`);
    } catch (err) {
      console.error("Failed to update ticket", err);
      setSaveError("Unable to update ticket. Please try again.");
    } finally {
      setIsSaving(false);
    }
  };

  useEffect(() => {
    const fetchTicket = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await getTicketById(id);
        setTicketTitle(data.title);
        setTicketDescription(data.description);
        setTicketStatus(data.status);
        setTicketPriority(data.priority);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchTicket();
  }, [id]);

  return (
    <div>
      <h2>Edit Ticket</h2>

      {loading && <p>Loading ticket...</p>}

      {saveError && <p>{saveError}</p>}

      {error && <p>{error}</p>}

      {!loading && !error && (
        <form onSubmit={handleSubmit}>
          <TicketForm
            ticketTitle={ticketTitle}
            setTicketTitle={setTicketTitle}
            ticketDescription={ticketDescription}
            setTicketDescription={setTicketDescription}
            ticketStatus={ticketStatus}
            setTicketStatus={setTicketStatus}
            ticketPriority={ticketPriority}
            setTicketPriority={setTicketPriority}
            validationErrors={validationErrors}
          />

          <button type="submit" disabled={isSaving}>
            {isSaving ? "Saving..." : " Save Changes"}
          </button>
        </form>
      )}
    </div>
  );
}

export default EditTicket;
