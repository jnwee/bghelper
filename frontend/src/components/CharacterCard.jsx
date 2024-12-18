import React from "react";
import "@/app/css/character_card.css";

const CharacterCard = ({ id, name, imageUrl, dead }) => {
  const handleClick = () => {
    window.location.assign(`/characters/${id}`);
  };

  return (
    <div
      className="character-card"
      onClick={handleClick}
      role="button"
      tabIndex="0"
      onKeyDown={(e) => e.key === "Enter" && handleClick()}
    >
      {/* Image Section */}
      {imageUrl ? (
        <img
          src={imageUrl}
          alt={name}
          className={`character-image ${dead ? "greyed-out" : ""}`}
        />
      ) : (
        <div className="character-placeholder">No Image</div>
      )}

      {/* Character Name */}
      <div className="character-name">{name}</div>
    </div>
  );
};

export default CharacterCard;
