import React from "react";
import Link from "next/link";
import "./button.css";

export default function Button({
  variant = "action", // action | link | toggle | close
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
  if (variant === "link") {
    return (
      <Link href={href} className="button link-button">
        {iconClass && <i className={`icon ${iconClass}`}></i>}
        {label}
      </Link>
    );
  }

  if (variant === "toggle") {
    return (
      <button
        className={`button toggle-button ${isToggled ? "active" : ""}`}
        onClick={onClick}
      >
        {iconClass && <i className={`icon ${iconClass}`}></i>}
        {isToggled ? activeText : inactiveText}
      </button>
    );
  }

  if (variant === "close") {
    return (
      <button
        type="button"
        className="button close-button"
        onClick={onClick}
        aria-label="Close"
      >
        <i className="bi bi-x-square"></i>
      </button>
    );
  }

  return (
    <button
      type={type}
      className={`button action-button`}
      onClick={onClick}
      disabled={disabled}
    >
      {iconClass && <i className={`${iconClass}`}></i>}
      {label}
    </button>
  );
}
