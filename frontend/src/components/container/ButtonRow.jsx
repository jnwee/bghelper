import React from "react";

export default function ButtonRow({ children }) {
  return (
    <div className={`d-flex justify-content-center mb-3 gap-3`}>{children}</div>
  );
}
