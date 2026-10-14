import "./App.css";
import TicketList from "./components/TicketList";
import { Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";

function App() {
  return (
    <div>
      <h1>IT Helpdesk Dashboard</h1>

      <nav>
        <Link to="/">Home</Link>
        <Link to="/tickets">View Tickets</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/tickets" element={<TicketList />} />
      </Routes>
    </div>
  );
}

export default App;
