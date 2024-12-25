import React, { useState, useEffect } from "react";
import { useNotification } from "@/context/NotificationContext";

import CompanionCard from "./CompanionCard";
import CompanionSelect from "./CompanionSelect";
import CharacterService from "@/service/characters/CharacterService";

export default function Party({
  gameVersion,
  characterId,
  initialParty,
  onUpdate,
}) {
  const { showNotification } = useNotification();
  const [party, setParty] = useState(
    initialParty || [null, null, null, null, null],
  );
  const [showSelect, setShowSelect] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(null);

  // Sync state with new initialParty when gameVersion changes
  useEffect(() => {
    setParty(initialParty || [null, null, null, null, null]);
  }, [initialParty]);

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
      const updatedCharacter =
        await CharacterService.getCharacterById(characterId);

      const updatedParty =
        gameVersion === "bg1"
          ? updatedCharacter.partyBg1
          : updatedCharacter.partyBg2;

      setParty(updatedParty);
      setShowSelect(false);
      onUpdate(updatedCharacter);
      showNotification("Companion set successfully", "success");
    } catch (error) {
      showNotification("Failed to update companion: " + error, "danger");
    }
  };

  return (
    <div className="party-overview d-flex gap-3 flex-wrap justify-content-center">
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
          onClose={() => setShowSelect(false)}
        />
      )}
    </div>
  );
}
