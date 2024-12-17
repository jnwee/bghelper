"use client";

import React, { useEffect, useState } from "react";
import CharacterCard from "../../components/CharacterCard";

export default function Home() {
  const [characters, setCharacters] = useState([]);

  useEffect(() => {
    fetch(`${process.env.NEXT_PUBLIC_API_URL}/characters`)
      .then((res) => res.json())
      .then((data) => setCharacters(data))
      .catch((err) => console.error("Error fetching characters:", err));
  }, []);

  return (
    <div className="container mt-5">
      <h1 className="mb-4 text-center">Character Roster</h1>

      {/* Display characters */}
      <div className="row">
        {characters.length > 0 ? (
          characters.map((char) => (
            <div className="col-md-4" key={char.id}>
              <CharacterCard
                name={char.name}
                dead={char.dead}
                imageUrl={char.imageUrl}
              />
            </div>
          ))
        ) : (
          <p className="text-center">No characters found. Add some, Captain!</p>
        )}
      </div>
    </div>
  );
}
