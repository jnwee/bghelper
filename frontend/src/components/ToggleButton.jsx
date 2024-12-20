import React from "react";
import "@/app/css/buttons.css";

export default function ToggleButton({ text, isToggled, onToggle }) {
  return (
    <button
      className={`toggle-button ${isToggled ? "active" : ""}`}
      onClick={onToggle}
    >
      {isToggled ? `Hide ${text}` : `Show ${text}`}
    </button>
  );
}
