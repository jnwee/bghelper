import React from "react";
import "./character_overview.css";

export default function CharacterOverview({
  race,
  characterClass,
  alignment,
  imageUrl,
}) {
  const formatLabel = (value) => {
    if (!value) {
      return "";
    }
    return value
      .replace(/_/g, " ")
      .replace(/7/g, "/")
      .toLowerCase()
      .replace(/(^|[ /])[a-z]/g, (match) => match.toUpperCase());
  };

  return (
    <div className="character-overview d-flex flex-column align-items-center">
      {imageUrl ? (
        <img
          src={imageUrl}
          alt="portrait not found"
          className="character-image"
        />
      ) : (
        <div className="character-placeholder">No Image</div>
      )}
      <p className="mt-4">{formatLabel(race)}</p>
      <p className="mt-2">{formatLabel(characterClass)}</p>
      <p className="mt-2">{formatLabel(alignment)}</p>
    </div>
  );
}
