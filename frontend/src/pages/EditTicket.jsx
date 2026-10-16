import { useEffect, useState } from "react";
import { getAllTickets } from "../services/ticketService";
import TicketSelection from "../components/TicketSelection";

function EditTicket() {
  const [tickets, setTickets] = useState([]);
  const [error, setError] = useState("");
  const [selectedTicketId, setSelectedTicketId] = useState("");

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
      <h2>Edit Ticket</h2>

      {error && <p>{error}</p>}

      <TicketSelection
        tickets={tickets}
        selectedTicketId={selectedTicketId}
        setSelectedTicketId={setSelectedTicketId}
      />
    </div>
  );
}

export default EditTicket;
