import React, { useState } from "react";
import { useNotification } from "@/context/NotificationContext/NotificationContext";
import CharacterService from "@/services/characters/CharacterService";
import Button from "@/components/Button";

export default function DeathNote({ characterId, initialText }) {
  const { showNotification } = useNotification();
  const [text, setText] = useState(initialText);
  const [editMode, setEditMode] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleToggleMode = () => {
    if (editMode) {
      saveDeathNote();
    }
    setEditMode(!editMode);
  };

  const saveDeathNote = async () => {
    setLoading(true);
    try {
      const response = await CharacterService.setCharacterDeathNote(
        characterId,
        text,
      );
      showNotification("Death Note updated successfully.", "success");
    } catch (error) {
      showNotification("An error occurred: " + error.message, "danger");
    } finally {
      setLoading(false);
    }
  };

  function formatTextWithLineBreaks(text) {
    return text.split("\n").map((line, index) => (
      <React.Fragment key={index}>
        {line}
        <br />
      </React.Fragment>
    ));
  }

  return (
    <div className="death-note-container">
      {editMode ? (
        <textarea
          className="death-note-input"
          value={text}
          onChange={(e) => setText(e.target.value)}
          rows={10}
        />
      ) : (
        <div className="death-note-read">
          <p>{formatTextWithLineBreaks(text)}</p>
        </div>
      )}
      <Button
        variant="icon"
        onClick={handleToggleMode}
        iconClass={editMode ? "bi-check-circle" : "bi-pencil-square"}
        disabled={loading}
      />
    </div>
  );
}
