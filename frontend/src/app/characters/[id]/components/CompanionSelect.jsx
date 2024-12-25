import React, { useEffect, useState } from "react";
import CompanionCard from "./CompanionCard";
import { fetchCompanionsByGame } from "@/service/EnumService";

import "./companion_select.css";

export default function CompanionSelect({
  gameVersion,
  selectedCompanions,
  onSelect,
  onClose,
}) {
  const [companionOptions, setCompanionOptions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const companions = await fetchCompanionsByGame(gameVersion);
        // Filter out already selected companions
        const filteredOptions = companions.filter(
          (comp) => !selectedCompanions.includes(comp),
        );
        setCompanionOptions(filteredOptions);
      } catch (error) {
        console.error("Error fetching companions", error);
      }
    };

    fetchData();
  }, [gameVersion, selectedCompanions]);

  return (
    <div className="companion-modal-overlay">
      <div className="companion-modal-window">
        <div className="companion-modal-header">
          <h2>Select Companion</h2>
          <button className="companion-close-button" onClick={onClose}>
            X
          </button>
        </div>

        <div className="companion-modal-content d-flex gap-3 flex-wrap">
          {companionOptions.map((companion) => (
            <CompanionCard
              key={companion}
              companionName={companion}
              gameVersion={gameVersion}
              onClick={() => onSelect(companion)}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
