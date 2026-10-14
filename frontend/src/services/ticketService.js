const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

async function handleResponse(response) {
  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

// GET all tickets service layer
export async function getAllTickets() {
  const response = await fetch(`${API_URL}/tickets`);
  return handleResponse(response);
}

// GET ticket by ID service layer
export async function getTicketById(id) {
  const response = await fetch(`${API_URL}/tickets/${id}`);
  return handleResponse(response);
}

// POST create ticket service layer
export async function createTicket(ticket) {
  const response = await fetch(`${API_URL}/tickets`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(ticket),
  });

  return handleResponse(response);
}

// PUT update ticket service layer
export async function updateTicket(id, ticket) {
  const response = await fetch(`${API_URL}/tickets/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(ticket),
  });

  return handleResponse(response);
}

// DELETE ticket service layer
export async function deleteTicket(id) {
  const response = await fetch(`${API_URL}/tickets/${id}`, {
    method: "DELETE",
  });

  return handleResponse(response);
}
