"use client"; // Enables client-side interactivity like onClick

import Link from "next/link";
import { useRouter } from "next/navigation";

export default function Home() {
  const router = useRouter();

  const handleNavigate = () => {
    alert("Ye clicked me, Captain! Navigating to the About page...");
    router.push("/about");
  };

  return (
    <div className="container text-center mt-5">
      <h1 className="display-4">Welcome Aboard, Matey!</h1>
      <p className="lead mb-4">
        This be the mighty homepage of our Next.js vessel, styled with
        Bootstrap.
      </p>

      {/* Bootstrap Buttons */}
      <div className="mb-3">
        <Link href="/about">
          <button className="btn btn-primary mx-2">Go to About Page</button>
        </Link>
        <button onClick={handleNavigate} className="btn btn-warning mx-2">
          Click to Navigate
        </button>
      </div>

      {/* Bootstrap Links */}
      <div>
        <Link href="/characters" className="btn btn-success">
          View Characters
        </Link>
      </div>

      <div>
        <a href="/characters/create" className="btn btn-success">
          Create New Character
        </a>
      </div>
    </div>
  );
}
