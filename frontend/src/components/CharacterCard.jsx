import React from "react";

const CharacterCard = ({ name, dead, imageUrl }) => {
  return (
    <div className="card" style={{ width: "220px" }}>
      {imageUrl ? (
        <img
          src={imageUrl}
          className="card-img-top"
          alt={name}
          style={{ height: "250px", objectFit: "cover" }}
        />
      ) : (
        <div
          className="card-img-top text-white d-flex align-items-center justify-content-center"
          style={{ height: "200px" }}
        >
          No Image
        </div>
      )}

      {/* Character Details */}
      <div className="card-body">
        <h5 className="card-title">{name}</h5>
        <p className="card-text">Status: {dead ? "💀 Dead" : "⚔️ Alive"}</p>
      </div>
    </div>
  );
};

export default CharacterCard;
