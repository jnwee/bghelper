"use client"; // Enables client-side interactivity like onClick

import Link from "next/link";
import { useRouter } from "next/navigation";
import LinkButton from "@/components/LinkButton";

export default function Home() {
  const router = useRouter();

  return (
    <main className="bg-home d-flex justify-content-center algin-items-center">
      <div className="container text-center mt-5">
        <h1 className="display-4">YOUR BALDUR'S GATE COMPANION</h1>
        <p className="lead mb-4">Don't expect too much of this application</p>

        <div className="mb-3">
          <LinkButton
            href="/characters"
            iconClass="bi-arrow-right" /* Bootstrap Icons class */
            label="View Characters"
          />

          <LinkButton
            href="/add"
            iconClass="bi-plus-circle" /* Another icon */
            label="Add Character"
          />
        </div>
      </div>
    </main>
  );
}
