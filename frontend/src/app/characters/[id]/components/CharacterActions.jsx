import React, { useState, useEffect } from "react";
import Button from "@/components/Button";
import ProgressDiagram from "./ProgressDiagram";
import { useModal } from "@/context/ModalContext/ModalContext";
import { useNotification } from "@/context/NotificationContext/NotificationContext";
import CharacterService from "@/services/characters/CharacterService";

export default function CharacterActions({ character, onUpdate }) {
  const { showModal } = useModal();
  const { showNotification } = useNotification();

  const [progressState, setProgressState] = useState(2);

  useEffect(() => {
    switch (character.progress) {
      case "BG1":
        setProgressState(2);
        break;
      case "BG2":
        setProgressState(3);
        break;
      case "TOB":
        character.status === "ASCENDED"
          ? setProgressState(5)
          : setProgressState(4);
        break;
      default:
        setProgressState(2);
    }
  }, [character]);

  const handleLetDie = async () => {
    showModal(
      "Kill Character",
      <p>
        Are you sure you want to mark {character.name} as dead? This action
        cannot be undone.
      </p>,
      async () => {
        try {
          const updatedCharacter = await CharacterService.letCharacterDie(
            character.id,
          );
          onUpdate(updatedCharacter);
          showNotification("Character marked as dead successfully.", "success");
        } catch (error) {
          showNotification(`Fail: ${error.message}`, "danger");
        }
      },
    );
  };

  const handleProgress = async () => {
    try {
      const updatedCharacter = await CharacterService.advanceCharacter(
        character.id,
      );
      onUpdate(updatedCharacter);
      setProgressState(progressState + 1);
      showNotification("Character advanced successfully.", "success");
    } catch (error) {
      showNotification(`Fail: ${error.message}`, "danger");
    }
  };

  return (
    <>
      {character.status === "ALIVE" && (
        <>
          <ProgressDiagram
            currentStep={progressState}
            onAdvance={handleProgress}
          />
          <Button
            variant="action"
            label={"Mark as dead"}
            onClick={handleLetDie}
            iconClass="bi-fire"
          />
        </>
      )}
      {character.status === "ASCENDED" && (
        <p>
          {character.name} ascended after battling through Baldur's Gate I,
          Shadows of Amn and Throne of Bhaal!
        </p>
      )}
      {character.status === "DEAD" && (
        <p>
          {character.name} has fallen. They made it no further than{" "}
          {character.progress === "BG1"
            ? "Baldur's Gate I"
            : character.progress === "BG2"
              ? "Baldur's Gate II SoA"
              : "Throne of Bhaal"}
        </p>
      )}
    </>
  );
}
