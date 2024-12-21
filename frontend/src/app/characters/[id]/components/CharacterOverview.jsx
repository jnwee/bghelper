import React from "react";
import "./character_overview.css";

export default function CharacterOverview({ name, imageUrl }) {
  return (
    <div className="character-overview d-flex flex-column align-items-center">
      {imageUrl ? (
        <img src={imageUrl} alt={name} className="character-image" />
      ) : (
        <div className="character-placeholder">No Image</div>
      )}
      <h2 className="character-name">{name}</h2>
    </div>
  );
}
