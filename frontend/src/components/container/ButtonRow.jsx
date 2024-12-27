import React from "react";

export default function ButtonRow({ children, leftCount = 2 }) {
  const leftButtons = React.Children.toArray(children).slice(0, leftCount);
  const rightButtons = React.Children.toArray(children).slice(leftCount);

  return (
    <div className="d-flex justify-content-between align-items-center mb-3 gap-3">
      <div className="d-flex gap-2">{leftButtons}</div>
      <div className="d-flex gap-2">{rightButtons}</div>
    </div>
  );
}
