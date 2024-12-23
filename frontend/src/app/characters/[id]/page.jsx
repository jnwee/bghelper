"use client";

import React, { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import { useModal } from "@/context/ModalContext";
import { useNotification } from "@/context/NotificationContext";

import ImageService from "@/service/ImageService";
import CharacterService from "@/service/characters/CharacterService";

import Column from "@/components/container/Column";
import Button from "@/components/Button";
import CharacterOverview from "./components/CharacterOverview";
import Header from "@/components/Header";
import PageContainer from "@/components/container/PageContainer";
import ButtonRow from "@/components/container/ButtonRow";
import CharacterActions from "./components/CharacterActions";

export default function CharacterPage() {
  const router = useRouter();
  const { showModal } = useModal();
  const { showNotification } = useNotification();

  const [character, setCharacter] = useState(null);
  const [error, setError] = useState(null);
  const [hasNotified, setHasNotified] = useState(false);

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
  }, [character_id, hasNotified, showNotification]);

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
          <CharacterOverview name={character.name} imageUrl={imageUrl} />
        </Column>

        {/* Column 2: Actions */}
        <Column colSize="col-md-4">
          <CharacterActions character={character} onUpdate={setCharacter} />
        </Column>

        {/* Column 3: Party */}
        <Column colSize="col-md-4"></Column>
      </div>
    </PageContainer>
  );
}
