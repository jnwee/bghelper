import React from "react";
import CharacterRow from "./CharacterRow";

export default function CharacterSection({
  title,
  characters,
  error,
  fallbackMessage,
}) {
  return (
    <div className="mt-3">
      <h2 className="text-center">{title}</h2>

      {error && <p className="text-danger text-center">{error}</p>}

      {characters.length > 0 ? (
        <CharacterRow characters={characters} />
      ) : (
        <p className="text-center">{fallbackMessage}</p>
      )}
    </div>
  );
}
