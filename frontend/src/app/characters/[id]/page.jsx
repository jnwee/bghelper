"use client";

import React, { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import "@/app/characters/[id]/character_page.css";
import CharacterService from "@/service/CharacterService";

export default function CharacterPage() {
  // Use useParams() to get dynamic route params
  const params = useParams();
  const character_id = params.id;

  const [character, setCharacter] = useState(null);
  const [error, setError] = useState(null);

  const router = useRouter();

  useEffect(() => {
    const fetchCharacter = async () => {
      try {
        const data = await CharacterService.getCharacterById(character_id);
        setCharacter(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchCharacter();
  }, [character_id]);

  if (error) {
    return <p className="text-danger text-center">{error}</p>;
  }

  if (!character) {
    return <p className="text-center">Loading...</p>;
  }

  return (
    <div className="character-detail-container">
      {/* Header */}
      <header className="character-header">
        <h1>{character.name}</h1>
      </header>

      {/* Three Columns */}
      <div className="character-detail-columns">
        {/* Column 1: Image */}
        <div className="character-column">
          {character.imageUrl ? (
            <img
              src={character.imageUrl}
              alt={character.name}
              className="character-image"
            />
          ) : (
            <div className="character-placeholder">No Image</div>
          )}
        </div>

        {/* Column 2: Name */}
        <div className="character-column">
          <h2 className="character-name">{character.name}</h2>
        </div>

        {/* Column 3: Empty */}
        <div className="character-column"></div>
      </div>
    </div>
  );
}
