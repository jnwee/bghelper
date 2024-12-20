import React from "react";
import "@/app/css/buttons.css";

export default function ToggleButton({
  inactiveText,
  activeText,
  isToggled,
  onToggle,
  iconClass,
}) {
  return (
    <button
      className={`toggle-button ${isToggled ? "active" : ""}`}
      onClick={onToggle}
    >
      {iconClass && <i className={`${iconClass} me-2`}></i>}{" "}
      {/* Bootstrap Icon */}
      {isToggled ? `${inactiveText}` : `${activeText}`}
    </button>
  );
}
