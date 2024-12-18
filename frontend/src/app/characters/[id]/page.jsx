"use client";

import React, { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import "@/app/characters/[id]/character_page.css";
import CharacterService from "@/service/CharacterService";
import "@/app/css/buttons.css";

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

  const handleLetDie = async () => {
    if (
      confirm(
        `Are you sure you want to mark ${character.name} as dead? This action cannot be undone.`,
      )
    ) {
      try {
        await CharacterService.letCharacterDie(character_id);
        alert(`${character.name} has been marked as dead.`);
        // Refresh character data
        const updatedCharacter =
          await CharacterService.getCharacterById(character_id);
        setCharacter(updatedCharacter);
      } catch (error) {
        alert(`Failed to let character die: ${error.message}`);
      }
    }
  };

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
        <div className="character-column">
          {/* Let Character Die Button */}
          {!character.dead && (
            <button className="action-button mt-3" onClick={handleLetDie}>
              Let Character Die
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
