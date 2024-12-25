import React from "react";

export default function CompanionCard({ companionName, gameVersion, onClick }) {
  const formatName = (name) => {
    return name
      .replace(/_/g, " ")
      .toLowerCase()
      .replace(/(^| )[a-z]/g, (match) => match.toUpperCase());
  };

  const portraitPath = companionName
    ? `/assets/portraits/${gameVersion.toLowerCase()}/${companionName}.png`
    : `/assets/portraits/empty_slot.png`;

  return (
    <div
      className="companion-card d-flex flex-column align-items-center gap-2"
      onClick={onClick}
    >
      <img
        src={portraitPath}
        alt={companionName || "Empty"}
        className="companion-card-img"
      />
      <div className="companion-name">
        {companionName ? formatName(companionName) : "Empty Slot"}
      </div>
    </div>
  );
}
