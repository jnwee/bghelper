"use client";

import React, { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import { useModal } from "@/context/ModalContext";
import { useNotification } from "@/context/NotificationContext";
import "./character_page.css";
import "@/app/css/buttons.css";
import Row from "@/components/Row";
import Column from "@/components/Column";
import ActionButton from "@/components/ActionButton";
import CharacterOverview from "./components/CharacterOverview";
import Header from "@/components/Header";
import PageContainer from "@/components/PageContainer";
import ImageService from "@/service/ImageService";
import CharacterService from "@/service/characters/CharacterService";

export default function CharacterPage() {
  const router = useRouter();
  const { showModal } = useModal();
  const { showNotification } = useNotification();

  const params = useParams();
  const character_id = params.id;

  const [character, setCharacter] = useState(null);
  const [error, setError] = useState(null);

  const imageUrl = ImageService.getCharacterPortrait(character_id);

  useEffect(() => {
    const fetchCharacter = async () => {
      try {
        const data = await CharacterService.getCharacterById(character_id);
        setCharacter(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchCharacter();
  }, [character_id]);

  if (error) {
    return <p className="text-danger text-center">{error}</p>;
  }

  if (!character) {
    return <p className="text-center">Loading...</p>;
  }

  const handleLetDie = async () => {
    showModal(
      "Kill Character",
      <p>
        Are you sure you want to mark {character.name} as dead? This action
        cannot be undone.
      </p>,
      async () => {
        try {
          await CharacterService.letCharacterDie(character_id);
          const updatedCharacter =
            await CharacterService.getCharacterById(character_id);
          setCharacter(updatedCharacter);
          showNotification("Character marked as dead succesfully!", "success");
        } catch (error) {
          showNotification(`Fail: ${error.message}`, "danger");
        }
      },
    );
  };

  const handleDelete = async () => {
    try {
      await CharacterService.deleteCharacter(character_id);
      router.push("/characters");
      showNotification("Character was deleted succesfully!", "success");
    } catch (error) {
      showNotification("Failed to delete character.", "danger");
    }
  };

  return (
    <PageContainer>
      {/* Header */}
      <Header title={character.name} useH2={true} />

      {/* Three-Column Layout */}
      <Row className="h-100">
        {/* Column 1: Character Overview */}
        <Column colSize="col-md-4 d-flex align-items-start justify-content-center">
          <CharacterOverview name={character.name} imageUrl={imageUrl} />
        </Column>

        {/* Column 2: Actions */}
        <Column colSize="col-md-4 align-items-start justify-content-center d-flex">
          {!character.dead && (
            <ActionButton
              label={"Mark character as dead"}
              onClick={handleLetDie}
            />
          )}
          <ActionButton
            label={"Delete this character"}
            onClick={handleDelete}
          />
        </Column>

        {/* Column 3: Party */}
        <Column colSize="col-md-4 d-flex flex-column align-items-center"></Column>
      </Row>
    </PageContainer>
  );
}
