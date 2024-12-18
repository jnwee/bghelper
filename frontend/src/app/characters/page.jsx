"use client";

import React, { useEffect, useState } from "react";
import CharacterCard from "@/components/CharacterCard";
import CharacterService from "@/service/CharacterService";
import CharacterRow from "@/components/CharacterRow";

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
    <main className="d-flex justify-content-center align-items-center">
      <div className="container mt-5">
        <h1 className="mb-5 text-center">Characters</h1>

        {error && <p className="text-danger text-center">{error}</p>}

        {characters.length > 0 ? (
          <CharacterRow characters={characters} />
        ) : (
          <p className="text-center">Pretty empty here right now.</p>
        )}
      </div>
    </main>
  );
}
