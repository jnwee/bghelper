import React from "react";

export default function PageContainer({ children }) {
  return (
    <main className="d-flex justify-content-center align-items-center">
      <div className="container text-center mt-5">{children}</div>
    </main>
  );
}
