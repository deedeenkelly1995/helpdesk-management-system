import { useState } from "react";
import { createTicket } from "../services/ticketService";
import TicketForm from "../components/TicketForm";

function CreateTicket() {
  const [ticketTitle, setTicketTitle] = useState("");
  const [ticketDescription, setTicketDescription] = useState("");
  const [ticketStatus, setTicketStatus] = useState("");
  const [ticketPriority, setTicketPriority] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage("");
    setErrorMessage("");

    const newTicket = {
      title: ticketTitle,
      description: ticketDescription,
      status: ticketStatus,
      priority: ticketPriority,
    };

    try {
      setIsSaving(true);
      await createTicket(newTicket);
      console.log("Ticket created");
      setSuccessMessage("Ticket created Successfully!");
      // Reset the form inputs after a successful entry
      setTicketTitle("");
      setTicketDescription("");
      setTicketStatus("");
      setTicketPriority("");
    } catch (error) {
      console.error("Failed to create ticket", error);
      setErrorMessage("Unable to create ticket. Please try again.");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div>
      <h2>Create Ticket</h2>

      {successMessage && <p>{successMessage}</p>}
      {errorMessage && <p>{errorMessage}</p>}

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
        />

        <button type="submit" disabled={isSaving}>
          {isSaving ? "Saving..." : " Create Ticket"}
        </button>
      </form>
    </div>
  );
}

export default CreateTicket;
