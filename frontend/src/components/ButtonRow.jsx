import React from "react";

export default function ButtonRow({ children, alignment = "center" }) {
  // Convert alignment prop to CSS classes
  const alignmentClass =
    {
      left: "justify-content-start",
      center: "justify-content-center",
      right: "justify-content-end",
    }[alignment] || "justify-content-center";

  return <div className={`d-flex ${alignmentClass} mb-3`}>{children}</div>;
}
