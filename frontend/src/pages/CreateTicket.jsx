import { useState } from "react";
import { createTicket } from "../services/ticketService";

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
        <label htmlFor="ticketTitle">Ticket Title:</label>
        <input
          type="text"
          value={ticketTitle}
          onChange={(e) => setTicketTitle(e.target.value)}
          required
        />

        <label htmlFor="ticketDescription">Ticket Description:</label>
        <textarea
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
          {/*Inventory status drop down*/}
          {/* <option value="ACTIVE">ACTIVE</option>
      <option value="RETIRED">RETIRED</option>
      <option value="SHIPPING">SHIPPING</option>
      <option value="DISPOSAL">DISPOSAL</option>
      <option value="TRANSFER">TRANSFER</option>
      <option value="OUT FOR MAINTENCE">OUT FOR MAINTENCE</option>
      <option value="MAINTENCE IN PROGRESS">MAINTENCE IN PROGRESS</option> */}
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

        <button type="submit" disabled={isSaving}>
          {isSaving ? "Saving..." : " Create Ticket"}
        </button>
      </form>
    </div>
  );
}

export default CreateTicket;
