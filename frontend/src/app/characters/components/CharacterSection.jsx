import React from "react";
import CharacterRow from "./CharacterRow";

export default function CharacterSection({
  title,
  characters,
  fallbackMessage,
}) {
  return (
    <div className="mt-3">
      <h2 className="text-center">{title}</h2>

      {characters.length > 0 ? (
        <CharacterRow characters={characters} />
      ) : (
        <p className="text-center m-3">{fallbackMessage}</p>
      )}
    </div>
  );
}
