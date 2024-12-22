import React from "react";
import "@/app/css/character_card.css";
import ImageService from "@/service/ImageService";

const CharacterCard = ({ id, name, status }) => {
  const handleClick = () => {
    window.location.assign(`/characters/${id}`);
  };

  const imageUrl = ImageService.getCharacterPortrait(id);

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
          alt={"portrait not found"}
          className={`character-image ${String(status) === "DEAD" ? "greyed-out" : ""}`}
        />
      ) : (
        <div className="character-placeholder">No Portrait</div>
      )}

      {/* Character Name */}
      <div className="character-name">{name}</div>
    </div>
  );
};

export default CharacterCard;
