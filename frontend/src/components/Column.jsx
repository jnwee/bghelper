import React from "react";

export default function Column({
  children,
  colSize = "col-md-6",
  className = "",
  style = {},
}) {
  return (
    <div className={`${colSize} ${className}`} style={style}>
      {children}
    </div>
  );
}
