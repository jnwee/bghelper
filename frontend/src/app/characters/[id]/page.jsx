"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export default function CharacterPage({ params }) {
  const { id } = params; // This be the dynamic route param
  const [character, setCharacter] = useState(null);

  useEffect(() => {
    // Fetch character data from yer backend
    fetch(`${process.env.NEXT_PUBLIC_API_URL}/characters/${id}`)
      .then((res) => res.json())
      .then((data) => setCharacter(data))
      .catch((err) => console.error("Failed to fetch character:", err));
  }, [id]);

  if (!character) {
    return <p className="text-center mt-5">Loading character data...</p>;
  }

  return (
    <div className="container mt-5">
      <h1 className="display-5">{character.name}</h1>
      <p>Status: {character.dead ? "Dead 💀" : "Alive ⚔️"}</p>
      <a href="/characters" className="btn btn-secondary mt-3">
        Back to Characters
      </a>
    </div>
  );
}
