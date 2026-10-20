import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getTicketById, updateTicket } from "../services/ticketService";

function EditTicketForm() {
  const { id } = useParams();
  const [ticket, setTicket] = useState(null);
  const [error, setError] = useState("");
  const [ticketTitle, setTicketTitle] = useState("");
  const [ticketDescription, setTicketDescription] = useState("");
  const [ticketStatus, setTicketStatus] = useState("");
  const [ticketPriority, setTicketPriority] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  useEffect(() => {
    const fetchTicket = async () => {
      try {
        setError("");

        const data = await getTicketById(id);
        setTicket(data);
        setTicketTitle(data.title);
        setTicketDescription(data.description);
        setTicketStatus(data.status);
        setTicketPriority(data.priority);
      } catch (err) {
        setError(err.message);
      }
    };
    fetchTicket();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage("");
    setErrorMessage("");

    const updatedTicket = {
      title: ticketTitle,
      description: ticketDescription,
      status: ticketStatus,
      priority: ticketPriority,
    };

    try {
      setIsSaving(true);
      await updateTicket(id, updatedTicket);
      console.log("Ticket updated");
      setSuccessMessage("Ticket updated Successfully!");
    } catch (error) {
      console.error("Failed to update ticket", error);
      setErrorMessage("Unable to update ticket. Please try again.");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div>
      <h2>Edit Ticket Form</h2>

      {successMessage && <p>{successMessage}</p>}
      {errorMessage && <p>{errorMessage}</p>}
      {error && <p>{error}</p>}

      {ticket && (
        <div>
          {" "}
          <h3>{ticket.id}</h3>
          <form onSubmit={handleSubmit}>
            <h2>
              <label htmlFor="ticketTitle">Ticket Title:</label>
              <input
                id="ticketTitle"
                type="text"
                value={ticketTitle}
                placeholder="Enter Ticket Title"
                onChange={(e) => setTicketTitle(e.target.value)}
                required
              />
            </h2>
            <p>
              {" "}
              <label htmlFor="ticketDescription">Ticket Description:</label>
              <textarea
                id="ticketDescription"
                value={ticketDescription}
                placeholder="Enter Ticket Description"
                onChange={(e) => setTicketDescription(e.target.value)}
                required
              />
            </p>
            <p>
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
            </p>
            <p>
              <label htmlFor="ticketPriority">Priority:</label>
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
            </p>
            <hr />
            <button type="submit" disabled={isSaving}>
              {isSaving ? "Saving..." : " Update Ticket"}
            </button>
          </form>
        </div>
      )}
    </div>
  );
}

export default EditTicketForm;
