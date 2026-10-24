import { createContext, useState, useContext } from "react";

const AppContext = createContext();

export function AppProvider({ children }) {
  const [user, setUser] = useState({
    name: "Ronin",
    email: "ronin@example.com",
    password: "",
  });

  const [projects, setProjects] = useState([
    { id: 1, name: "E-commerce Site", client: "Cape Town Retail", status: "In Progress", deadline: "2026-11-15" },
    { id: 2, name: "Portfolio Redesign", client: "Joburg Media", status: "Completed", deadline: "2026-10-01" },
    { id: 3, name: "Mobile App UI", client: "Durban Tech", status: "Pending", deadline: "2026-12-05" },
    { id: 4, name: "Marketing Landing Page", client: "Pretoria Growth", status: "In Progress", deadline: "2026-11-20" },
  ]);

  const addProject = (newProject) => {
    setProjects([...projects, { ...newProject, id: Date.now() }]);
  };

  // --- NEW FUNCTION ---
  // Updates the status of a specific project by its ID
  const updateProjectStatus = (projectId, newStatus) => {
    setProjects(
      projects.map((project) =>
        project.id === projectId ? { ...project, status: newStatus } : project
      )
    );
  };

  return (
    <AppContext.Provider value={{ user, setUser, projects, addProject, updateProjectStatus }}>
      {children}
    </AppContext.Provider>
  );
}

export function useApp() {
  return useContext(AppContext);
}