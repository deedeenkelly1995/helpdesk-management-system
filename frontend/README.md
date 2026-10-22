# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Oxc](https://oxc.rs)
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/)

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.

Technologies Used
Frontend
Vite
React Router
React
JavaScript

Backend
Java
Spring Boot
REST APIs
Maven

Database
PostgreSQL
Spring JPA/Hibernate

Developement Tools
VS Code
GitHub
JUnit
Mockito
Docker

Features

Running the Application with Docker

Installation

1. Clone the repository:
   git clone <your-repository-url>
   cd helpdesk

2. Create a .env file in the root directory

3. Build and start the containers
   docker compose up -d --build

4. Open the applications
   Frontend: http://localhost:5173
   Backend API: http://localhost:8080/api/tickets

Stopping the Application
docker compose down

API Endpoints

Future Improvements

Project Purpose
