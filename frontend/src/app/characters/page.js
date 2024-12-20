"use client";

import React, { useEffect, useState } from "react";
import CharacterService from "@/service/CharacterService";
import LinkButton from "@/components/LinkButton";
import PageContainer from "@/components/PageContainer";
import Header from "@/components/Header";
import ButtonRow from "@/components/ButtonRow";
import CharacterSection from "./components/CharacterSection";
import ToggleButton from "@/components/ToggleButton";

export default function CharactersPage() {
  const [aliveCharacters, setAliveCharacters] = useState([]);
  const [deadCharacters, setDeadCharacters] = useState([]);
  const [ascendedCharacters, setAscendedCharacters] = useState([]);
  const [bg1Characters, setBg1Characters] = useState([]);
  const [bg2Characters, setBg2Characters] = useState([]);
  const [tobCharacters, setTobCharacters] = useState([]);
  const [filterByProgress, setFilterByProgress] = useState(false);
  const [showAscended, setShowAscended] = useState(false);
  const [showDead, setShowDead] = useState(false);
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

        const bg1 = await CharacterService.getCharactersByProgress("BG1");
        const bg2 = await CharacterService.getCharactersByProgress("BG2");
        const tob = await CharacterService.getCharactersByProgress("TOB");
        setBg1Characters(bg1);
        setBg2Characters(bg2);
        setTobCharacters(tob);
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
        useH2={false}
        leadText="All your characters in one place. Isn't this great?"
        leadFontSize="1.5rem"
      />
      <ButtonRow>
        <LinkButton href="/" iconClass="bi-arrow-left" label="Home" />
        <LinkButton
          href="/characters/create"
          iconClass="bi-plus-circle"
          label="Add Character"
        />
      </ButtonRow>
      <ButtonRow>
        <ToggleButton
          text="Filter by Progress"
          isToggled={filterByProgress}
          onToggle={() => setFilterByProgress(!filterByProgress)}
        />
      </ButtonRow>

      {!filterByProgress ? (
        <>
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
        </>
      ) : (
        <>
          {/* Characters in Throne of Bhaal */}
          <CharacterSection
            title="Throne of Bhaal"
            characters={tobCharacters}
            error={error}
            fallbackMessage="Noone here."
          />

          {/* Dead Characters */}
          <CharacterSection
            title="Baldur's Gate II"
            characters={bg2Characters}
            error={error}
            fallbackMessage="Noone here."
          />

          {/* Ascended Characters */}
          <CharacterSection
            title="Baldur's Gate I"
            characters={bg1Characters}
            error={error}
            fallbackMessage="Noone here."
          />
        </>
      )}
    </PageContainer>
  );
}
