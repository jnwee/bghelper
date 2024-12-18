import React, { useState } from "react";
import CharacterCard from "@/components/CharacterCard";
import "@/app/css/character_row.css";

export default function CharacterRow({ characters }) {
  const [startIndex, setStartIndex] = useState(0);
  const itemsPerRow = 5;

  const visibleCharacters = characters.slice(
    startIndex,
    startIndex + itemsPerRow,
  );

  const handleNext = () => {
    if (startIndex + itemsPerRow < characters.length) {
      setStartIndex(startIndex + itemsPerRow);
    }
  };

  const handlePrevious = () => {
    if (startIndex > 0) {
      setStartIndex(startIndex - itemsPerRow);
    }
  };

  return (
    <div className="character-row-container position-relative my-4">
      {/* Previous Button */}
      {startIndex > 0 && (
        <button className="btn-navigate btn-previous" onClick={handlePrevious}>
          <i className="bi bi-arrow-bar-left"></i>
        </button>
      )}

      {/* Character Row */}
      <div className="character-row d-flex justify-content-between p-2">
        {visibleCharacters.map((character) => (
          <CharacterCard
            key={character.id}
            id={character.id}
            name={character.name}
            dead={character.dead}
          />
        ))}
      </div>

      {/* Next Button */}
      {startIndex + itemsPerRow < characters.length && (
        <button className="btn-navigate btn-next" onClick={handleNext}>
          <i className="bi bi-arrow-bar-right"></i>
        </button>
      )}
    </div>
  );
}
