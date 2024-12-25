import React, { useState } from "react";
import CompanionCard from "./CompanionCard";
import CompanionSelect from "./CompanionSelect";
import CharacterService from "@/service/characters/CharacterService";

export default function Party({ gameVersion, characterId }) {
  const [party, setParty] = useState([null, null, null, null, null]);
  const [showSelect, setShowSelect] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(null);

  const handleCompanionClick = (index) => {
    setSelectedIndex(index);
    setShowSelect(true);
  };

  const handleCompanionSelect = async (companion) => {
    try {
      const response = await CharacterService.setCharacterCompanion(
        characterId,
        gameVersion,
        selectedIndex,
        companion,
      );
      if (response.ok) {
        const updatedParty = [...party];
        updatedParty[selectedIndex] = companion;
        setParty(updatedParty);
      }
      setShowSelect(false);
    } catch (error) {
      console.error("Failed to update companion", error);
    }
  };

  return (
    <div className="party-overview d-flex gap-3 flex-wrap">
      {party.map((companion, index) => (
        <CompanionCard
          key={index}
          companionName={companion}
          gameVersion={gameVersion}
          onClick={() => handleCompanionClick(index)}
        />
      ))}

      {showSelect && (
        <CompanionSelect
          gameVersion={gameVersion}
          selectedCompanions={party}
          onSelect={handleCompanionSelect}
          onClose={() => setShowSelect(false)} // Close modal without selection
        />
      )}
    </div>
  );
}
