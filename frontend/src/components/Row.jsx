import React from "react";

export default function Row({ children, className = "", style = {} }) {
  return (
    <div className={`row g-4 ${className}`} style={style}>
      {children}
    </div>
  );
}
