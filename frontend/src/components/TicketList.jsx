import { useEffect, useState } from "react";
import { getAllTickets } from "../services/ticketService";
import { Link } from "react-router-dom";

function TicketList() {
  const [tickets, setTickets] = useState([]);
  const [error, setError] = useState("");

  //Loading
  const [loading, setLoading] = useState(true);

  //Search
  const [searchTerm, setSearchTerm] = useState("");

  // Filters
  const [selectedStatus, setSelectedStatus] = useState("ALL");
  const [selectedPriority, setSelectedPriority] = useState("ALL");

  const clearFilters = () => {
    setSearchTerm("");
    setSelectedStatus("ALL");
    setSelectedPriority("ALL");
  };

  const hasActiveFilters =
    searchTerm.trim() !== "" ||
    selectedStatus !== "ALL" ||
    selectedPriority !== "ALL";

  const filteredTickets = tickets.filter((ticket) => {
    const matchesSearch = ticket.title
      .toLowerCase()
      .includes(searchTerm.toLowerCase().trim());

    const ticketStatuses =
      selectedStatus === "ALL" || ticket.status === selectedStatus;

    const ticketPriorities =
      selectedPriority === "ALL" || ticket.priority === selectedPriority;

    return ticketStatuses && ticketPriorities && matchesSearch;
  });

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        setError("");

        const data = await getAllTickets();
        setTickets(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchTickets();
  }, []);

  return (
    <div>
      <h2>Helpdesk Tickets</h2>

      {error && <p>{error}</p>}

      {!loading && tickets.length === 0 && !error && <p>No tickets found.</p>}

      {!loading &&
        !error &&
        tickets.length > 0 &&
        filteredTickets.length === 0 && <p>No matching tickets found.</p>}

      {loading && <p> Loading tickets...</p>}

      {/*Ticket Search*/}
      <label htmlFor="ticketSearch">Search Tickets:</label>
      <input
        id="ticketSearch"
        type="text"
        placeholder="Search title of ticket..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      {/*Ticket Status Filter*/}
      <label htmlFor="ticketStatusFilter">Filter by Status:</label>
      <select
        id="ticketStatusFilter"
        value={selectedStatus}
        onChange={(e) => setSelectedStatus(e.target.value)}
      >
        <option value="ALL">All</option>
        <option value="OPEN">OPEN</option>
        <option value="IN_PROGRESS">IN PROGRESS</option>
        <option value="RESOLVED">RESOLVED</option>
      </select>
      {/*Ticket Priority Filter*/}
      <label htmlFor="ticketPriorityFilter">Filter by Priority:</label>
      <select
        id="ticketPriorityFilter"
        value={selectedPriority}
        onChange={(e) => setSelectedPriority(e.target.value)}
      >
        <option value="ALL">All</option>
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>

      <button type="button" onClick={clearFilters} disabled={!hasActiveFilters}>
        Clear Filters
      </button>

      {!loading && !error && tickets.length > 0 && (
        <p>
          Showing {filteredTickets.length} of {tickets.length} tickets
        </p>
      )}

      {!loading &&
        !error &&
        filteredTickets.map((ticket) => (
          <div key={ticket.id}>
            <Link to={`/tickets/${ticket.id}`}>
              <h3>{ticket.title}</h3>
            </Link>
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
