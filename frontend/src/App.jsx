import { useState } from "react";
import heroImg from "./assets/hero.png";
import reactLogo from "./assets/react.svg";
import viteLogo from "./assets/vite.svg";
import "./App.css";
import TicketList from "./components/TicketList";

function App() {
  return (
    <div>
      <h1>IT Helpdesk Dashboard</h1>
      <TicketList />
    </div>
  );
}

export default App;
