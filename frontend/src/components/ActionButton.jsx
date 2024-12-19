import React from "react";

export default function ActionButton({
  type = "button",
  label,
  iconClass = "",
  onClick = null,
  className = "",
  disabled = false,
}) {
  return (
    <button
      type={type}
      className={`action-button d-flex align-items-center justify-content-center ${className}`}
      onClick={onClick}
      disabled={disabled}
    >
      {iconClass && <i className={`${iconClass} me-2`}></i>} {/* Icon */}
      {label}
    </button>
  );
}
