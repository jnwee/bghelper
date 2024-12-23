import React from "react";

export default function PageContainer({ children }) {
  return (
    <main className="d-flex justify-content-center align-items-center container text-center mt-5">
      {children}
    </main>
  );
}
