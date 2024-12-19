import React from "react";

export default function Header({
  title,
  useH2 = false,
  leadText,
  leadFontSize = "1rem",
}) {
  const TitleTag = useH2 ? "h2" : "h1"; // Dynamically choose h1 or h2

  return (
    <div className="header text-center">
      <TitleTag className="display-5">{title}</TitleTag>
      <p className="lead mb-4" style={{ fontSize: leadFontSize }}>
        {leadText}
      </p>
    </div>
  );
}
