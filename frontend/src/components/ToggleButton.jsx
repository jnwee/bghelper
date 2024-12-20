import React from "react";
import "@/app/css/buttons.css";

export default function ToggleButton({ text, isToggled, onToggle, iconClass }) {
  return (
    <button
      className={`toggle-button ${isToggled ? "active" : ""}`}
      onClick={onToggle}
    >
      {iconClass && <i className={`${iconClass} me-2`}></i>}{" "}
      {/* Bootstrap Icon */}
      {isToggled ? `Hide ${text}` : `Show ${text}`}
    </button>
  );
}
