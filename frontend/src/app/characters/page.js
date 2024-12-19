"use client";

import React, { useEffect, useState } from "react";
import CharacterService from "@/service/CharacterService";
import LinkButton from "@/components/LinkButton";
import PageContainer from "@/components/PageContainer";
import Header from "@/components/Header";
import ButtonRow from "@/components/ButtonRow";
import CharacterSection from "./components/CharacterSection";

export default function CharactersPage() {
  const [aliveCharacters, setAliveCharacters] = useState([]);
  const [deadCharacters, setDeadCharacters] = useState([]);
  const [ascendedCharacters, setAscendedCharacters] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const alive = await CharacterService.getCharactersByStatus("ALIVE");
        const dead = await CharacterService.getCharactersByStatus("DEAD");
        const ascended =
          await CharacterService.getCharactersByStatus("ASCENDED");

        setAliveCharacters(alive);
        setDeadCharacters(dead);
        setAscendedCharacters(ascended);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchCharacters();
  }, []);

  return (
    <PageContainer>
      <Header
        title="Characters"
        useH2={true}
        leadText="All your characters in one place. Isn't this great?"
        leadFontSize="1rem"
      />
      <ButtonRow alignment="center">
        <LinkButton href="/" iconClass="bi-arrow-left" label="Home" />
        <LinkButton
          href="/characters/create"
          iconClass="bi-plus-circle"
          label="Add Character"
        />
      </ButtonRow>

      {/* Alive Characters */}
      <CharacterSection
        title="Challengers"
        characters={aliveCharacters}
        error={error}
        fallbackMessage="Pretty empty here right now."
      />

      {/* Dead Characters */}
      <CharacterSection
        title="Graveyard"
        characters={deadCharacters}
        error={error}
        fallbackMessage="No dead Characters yet. Good Job!"
      />

      {/* Ascended Characters */}
      <CharacterSection
        title="Hall of Ascended Characters"
        characters={ascendedCharacters}
        error={error}
        fallbackMessage="Noone made it yet. But that's fine. Just keep at it."
      />
    </PageContainer>
  );
}
