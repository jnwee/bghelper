import React from "react";

export default function Header({
  title,
  level = 1,
  leadText = "",
  leadFontSize = "1rem",
}) {
  const HeadingTag = `h${Math.min(Math.max(level, 1), 6)}`;

  return (
    <div className="header text-center">
      <HeadingTag className="display-5">{title}</HeadingTag>

      {leadText && (
        <p className="lead mb-4" style={{ fontSize: leadFontSize }}>
          {leadText}
        </p>
      )}
    </div>
  );
}
