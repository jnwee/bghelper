import React from "react";

export default function Column({ children, colSize = "col-md-6" }) {
  return (
    <div className={`${colSize} d-flex flex-column align-items-center`}>
      {children}
    </div>
  );
}
