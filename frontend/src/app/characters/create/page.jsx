"use client"; // Enables client-side interactivity

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function CreateCharacter() {
  const router = useRouter();

  // State to hold form inputs and messages
  const [name, setName] = useState("");
  const [dead, setDead] = useState(false);
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage(""); // Clear previous messages

    try {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/characters`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ name, dead }),
        },
      );

      if (!response.ok) {
        throw new Error(
          "Failed to create character. Backend response: " + response.status,
        );
      }

      setMessage("Character created successfully! ⚔️");
      setName(""); // Reset the form
      setDead(false);

      // Navigate back to the characters list after a delay
      setTimeout(() => router.push("/characters"), 2000);
    } catch (error) {
      console.error("Error creating character:", error);
      setMessage("Error: " + error.message);
    }
  };

  return (
    <div className="container mt-5">
      <h1 className="display-5 mb-4">Create a New Character</h1>

      {/* Success/Error Message */}
      {message && (
        <div
          className={`alert ${message.startsWith("Error") ? "alert-danger" : "alert-success"}`}
        >
          {message}
        </div>
      )}

      {/* Character Form */}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Character Name
          </label>
          <input
            type="text"
            id="name"
            className="form-control"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Enter character name"
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="dead" className="form-label">
            Is the Character Dead?
          </label>
          <select
            id="dead"
            className="form-select"
            value={dead}
            onChange={(e) => setDead(e.target.value === "true")}
          >
            <option value="false">No (Alive ⚔️)</option>
            <option value="true">Yes (Dead 💀)</option>
          </select>
        </div>

        <button type="submit" className="btn btn-primary">
          Create Character
        </button>
      </form>

      {/* Navigation Button */}
      <div className="mt-4">
        <a href="/characters" className="btn btn-secondary">
          Back to Characters
        </a>
      </div>
    </div>
  );
}
