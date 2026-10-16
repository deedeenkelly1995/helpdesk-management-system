import "./App.css";
import CreateTicket from "./pages/CreateTicket";
import TicketList from "./components/TicketList";
import TicketDetails from "./pages/TicketDetails";
import { Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import EditTicket from "./pages/EditTicket";
import EditTicketForm from "./pages/EditTicketForm";

function App() {
  return (
    <div>
      <h1>IT Helpdesk Dashboard</h1>

      <nav>
        <Link to="/">Home</Link>
        <Link to="/tickets">View Tickets</Link>
        <Link to="/tickets/new">Create Ticket</Link>
        <Link to="/tickets/edit">Edit Ticket</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/tickets" element={<TicketList />} />
        <Route path="/tickets/:id" element={<TicketDetails />} />
        <Route path="/tickets/new" element={<CreateTicket />} />
        <Route path="/tickets/edit" element={<EditTicket />} />
        <Route path="/tickets/:id/edit" element={<EditTicketForm />} />
      </Routes>
    </div>
  );
}

export default App;
