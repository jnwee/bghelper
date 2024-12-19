import React from "react";

export default function Message({ text, type = "success" }) {
  if (!text) return null; // Render nothing if no message

  const alertClass = type === "error" ? "alert-danger" : "alert-success";

  return <div className={`alert ${alertClass}`}>{text}</div>;
}
