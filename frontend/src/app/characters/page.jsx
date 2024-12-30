"use client";

import React, { useEffect, useState } from "react";
import { useNotification } from "@/context/NotificationContext/NotificationContext";

import CharacterFilterService from "@/services/characters/CharacterFilterService";

import Button from "@/components/Button";
import PageContainer from "@/components/container/PageContainer";
import Header from "@/components/Header";
import ButtonRow from "@/components/container/ButtonRow";
import CharacterSection from "./components/CharacterSection";

export default function CharactersPage() {
  const [aliveCharacters, setAliveCharacters] = useState([]);
  const [deadCharacters, setDeadCharacters] = useState([]);
  const [ascendedCharacters, setAscendedCharacters] = useState([]);
  const [bg1Characters, setBg1Characters] = useState([]);
  const [bg2Characters, setBg2Characters] = useState([]);
  const [tobCharacters, setTobCharacters] = useState([]);
  const [filterByProgress, setFilterByProgress] = useState(false);

  const { showNotification } = useNotification();

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const alive =
          await CharacterFilterService.getCharactersByStatus("ALIVE");
        const dead = await CharacterFilterService.getCharactersByStatus("DEAD");
        const ascended =
          await CharacterFilterService.getCharactersByStatus("ASCENDED");
        setAliveCharacters(alive);
        setDeadCharacters(dead);
        setAscendedCharacters(ascended);

        const bg1 = await CharacterFilterService.getCharactersByProgress("BG1");
        const bg2 = await CharacterFilterService.getCharactersByProgress("BG2");
        const tob = await CharacterFilterService.getCharactersByProgress("TOB");
        setBg1Characters(bg1);
        setBg2Characters(bg2);
        setTobCharacters(tob);
      } catch (error) {
        showNotification(error.message, "danger");
      }
    };

    fetchCharacters();
  }, []);

  return (
    <PageContainer>
      <Header
        title="Characters"
        level={1}
        leadText="All your characters in one place. Isn't this great?"
        leadFontSize="1.5rem"
      />
      <ButtonRow leftCount={2}>
        <Button
          variant="link"
          href="/"
          iconClass="bi-arrow-left"
          label="Home"
        />
        <Button
          variant="link"
          href="/characters/create"
          iconClass="bi-plus-circle"
          label="Add Character"
        />
        <Button
          variant="toggle"
          inactiveText="Graveyard"
          activeText="All"
          isToggled={filterByProgress}
          onClick={() => setFilterByProgress(!filterByProgress)}
          iconClass="bi bi-arrow-repeat"
        />
      </ButtonRow>

      {!filterByProgress ? (
        <>
          {/* Alive Characters */}
          <CharacterSection
            title="Challengers"
            characters={aliveCharacters}
            fallbackMessage="Pretty empty here right now."
          />

          {/* Dead Characters */}
          <CharacterSection
            title="Graveyard"
            characters={deadCharacters}
            fallbackMessage="No dead Characters yet. Good Job!"
          />

          {/* Ascended Characters */}
          <CharacterSection
            title="Hall of Ascended Characters"
            characters={ascendedCharacters}
            fallbackMessage="Noone made it yet. But that's fine. Just keep at it."
          />
        </>
      ) : (
        <>
          {/* Characters in Throne of Bhaal */}
          <CharacterSection
            title="Throne of Bhaal"
            characters={tobCharacters}
            fallbackMessage="Noone here."
          />

          {/* Dead Characters */}
          <CharacterSection
            title="Baldur's Gate II"
            characters={bg2Characters}
            fallbackMessage="Noone here."
          />

          {/* Ascended Characters */}
          <CharacterSection
            title="Baldur's Gate I"
            characters={bg1Characters}
            fallbackMessage="Noone here."
          />
        </>
      )}
    </PageContainer>
  );
}
