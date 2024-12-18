"use client";

import React, { useEffect, useState } from "react";
import CharacterCard from "@/components/CharacterCard";
import CharacterService from "@/service/CharacterService";

export default function CharactersPage() {
  const [characters, setCharacters] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const data = await CharacterService.getSortedCharacters(
          "createdAt",
          "desc",
        );
        setCharacters(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchCharacters();
  }, []);

  return (
    <main className="bg-home d-flex justify-content-center algin-items-center">
      <div className="container mt-5">
        <h1 className="mb-4 text-center">Characters</h1>

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
            <p className="text-center">Pretty empty here right now.</p>
          )}
        </div>
      </div>
    </main>
  );
}
