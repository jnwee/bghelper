"use client";

import React, { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import { useModal } from "@/context/ModalContext/ModalContext";
import { useNotification } from "@/context/NotificationContext/NotificationContext";

import ImageService from "@/services/ImageService";
import CharacterService from "@/services/characters/CharacterService";

import Column from "@/components/container/Column";
import Button from "@/components/Button";
import CharacterOverview from "./components/CharacterOverview";
import Header from "@/components/Header";
import PageContainer from "@/components/container/PageContainer";
import ButtonRow from "@/components/container/ButtonRow";
import CharacterActions from "./components/CharacterActions";
import Party from "./components/Party";
import "./character.css";
import DeathNote from "./components/DeathNote";

export default function CharacterPage() {
  const router = useRouter();
  const { showModal } = useModal();
  const { showNotification } = useNotification();

  const [character, setCharacter] = useState(null);
  const [error, setError] = useState(null);
  const [hasNotified, setHasNotified] = useState(false);
  const [showBg2, setShowBg2] = useState(false);

  const params = useParams();
  const character_id = params.id;
  const imageUrl = ImageService.getCharacterPortrait(character_id);

  useEffect(() => {
    const fetchCharacter = async () => {
      try {
        const data = await CharacterService.getCharacterById(character_id);
        setCharacter(data);
        setError(null);
        setHasNotified(false);
      } catch (err) {
        setError(err.message);
        if (!hasNotified) {
          showNotification("Error fetching Character from database.", "danger");
          setHasNotified(true);
        }
      }
    };

    fetchCharacter();
  }, [character_id]);

  const handleUpdate = (updatedCharacter) => {
    setCharacter({ ...updatedCharacter });
  };

  if (error) {
    return <p className="text-center">{error}</p>;
  }

  if (!character) {
    return <p className="text-center">Loading...</p>;
  }

  const handleDelete = async () => {
    showModal(
      "Delete Character",
      <p>
        Are you sure you want to delete {character.name}? This action cannot be
        undone.
      </p>,
      async () => {
        try {
          await CharacterService.deleteCharacter(character_id);
          await new Promise((resolve) => setTimeout(resolve, 300));
          router.push("/characters");
          showNotification("Character was deleted succesfully.", "success");
        } catch (error) {
          showNotification("Failed to delete character.", "danger");
        }
      },
    );
  };

  return (
    <PageContainer>
      <Header title={character.name} useH2={false} />

      <ButtonRow>
        <Button
          variant="link"
          href="/characters"
          label={"Back"}
          iconClass="bi-arrow-left"
        />
        <div className="d-flex flex-grow-1" />
        <Button
          variant="toggle"
          inactiveText="Baldur's Gate II"
          activeText="Baldur's Gate I"
          isToggled={showBg2}
          onClick={() => setShowBg2(!showBg2)}
          iconClass="bi-arrow-repeat"
        />
        <Button
          variant="action"
          onClick={handleDelete}
          label={"Delete character"}
          iconClass="bi-recycle"
        />
      </ButtonRow>

      {/* Three-Column Layout */}
      <div className="row h-100 mt-5">
        {/* Column 1: Character Overview */}
        <Column colSize="col-md-4">
          <CharacterOverview
            race={character.race}
            characterClass={character.characterClass}
            alignment={character.alignment}
            imageUrl={imageUrl}
          />
        </Column>

        {/* Column 2: Actions */}
        <Column colSize="col-md-4">
          <CharacterActions character={character} onUpdate={setCharacter} />
          {character.status == "DEAD" && (
            <>
              <h3 className="mt-4">Death Reason</h3>
              <DeathNote
                characterId={character.id}
                initialText={character.deathNote.replace(/\\n/g, "\n")}
              />
            </>
          )}
        </Column>

        {/* Column 3: Party */}
        <Column colSize="col-md-4">
          {showBg2 ? (
            <Party
              gameVersion={"bg2"}
              characterId={character_id}
              initialParty={character.partyBg2}
              onUpdate={handleUpdate}
            />
          ) : (
            <Party
              gameVersion={"bg1"}
              characterId={character_id}
              initialParty={character.partyBg1}
              onUpdate={handleUpdate}
            />
          )}
        </Column>
      </div>
    </PageContainer>
  );
}
