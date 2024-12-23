import React from "react";
import Link from "next/link"; // For Next.js routing
import "./css/buttons.css";

export default function Button({
  variant = "action", // action | link | toggle
  type = "button",
  href = "#",
  label,
  iconClass = "",
  onClick = null,
  disabled = false,
  isToggled = false,
  activeText,
  inactiveText,
}) {
  // Render LinkButton
  if (variant === "link") {
    return (
      <Link href={href} className="link-button">
        {iconClass && <i className={`icon ${iconClass} me-2`}></i>}
        {label}
      </Link>
    );
  }

  // Render ToggleButton
  if (variant === "toggle") {
    return (
      <button
        className={`toggle-button ${isToggled ? "active" : ""}`}
        onClick={onClick}
      >
        {iconClass && <i className={`${iconClass} me-2`}></i>}
        {isToggled ? inactiveText : activeText}
      </button>
    );
  }

  // Render ActionButton (default)
  return (
    <button
      type={type}
      className={`action-button`}
      onClick={onClick}
      disabled={disabled}
    >
      {iconClass && <i className={`${iconClass} me-2`}></i>}
      {label}
    </button>
  );
}
