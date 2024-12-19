"use client";

import React, { useEffect, useState } from "react";
import CharacterService from "@/service/CharacterService";
import CharacterRow from "@/components/CharacterRow";
import LinkButton from "@/components/LinkButton";

export default function CharactersPage() {
  const [aliveCharacters, setAliveCharacters] = useState([]);
  const [deadCharacters, setDeadCharacters] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const data = await CharacterService.getCharactersByStatus("ALIVE");
        setAliveCharacters(data);
        const data2 = await CharacterService.getCharactersByStatus("DEAD");
        setDeadCharacters(data2);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchCharacters();
  }, []);

  return (
    <main className="d-flex justify-content-center align-items-center">
      <div className="container mt-5">
        <h1 className="text-center">Characters</h1>
        <p className="text-center mb-2">
          All your characters in one place. Isn't this great?
        </p>
        <div className="d-flex justify-content-center align-items-center">
          <div className="my-3">
            <LinkButton
              href="/"
              iconClass="bi-arrow-left" /* Bootstrap Icons class */
              label="Home"
            />

            <LinkButton
              href="/characters/create"
              iconClass="bi-plus-circle" /* Another icon */
              label="Add Character"
            />
          </div>
        </div>

        <h2 className="text-center">Your Roster</h2>
        <div>
          {error && <p className="text-danger text-center">{error}</p>}

          {aliveCharacters.length > 0 ? (
            <CharacterRow characters={aliveCharacters} />
          ) : (
            <p className="text-center">Pretty empty here right now.</p>
          )}
        </div>
        <div className="mt-5">
          <h2 className="text-center">Graveyard</h2>

          {error && <p className="text-danger text-center">{error}</p>}

          {deadCharacters.length > 0 ? (
            <CharacterRow characters={deadCharacters} />
          ) : (
            <p className="text-center">No dead Characters yet. Good Job!</p>
          )}
        </div>
      </div>
    </main>
  );
}
