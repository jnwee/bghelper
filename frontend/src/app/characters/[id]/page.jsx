"use client";

import React, { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import "./character_page.css";
import CharacterService from "@/service/CharacterService";
import "@/app/css/buttons.css";
import Row from "@/components/Row";
import Column from "@/components/Column";
import ActionButton from "@/components/ActionButton";
import CharacterOverview from "./components/CharacterOverview";
import Header from "@/components/Header";
import PageContainer from "@/components/PageContainer";

export default function CharacterPage() {
  const params = useParams();
  const character_id = params.id;

  const [character, setCharacter] = useState(null);
  const [error, setError] = useState(null);

  const imageUrl = CharacterService.getCharacterImage(character_id);

  const router = useRouter();

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
    if (
      confirm(
        `Are you sure you want to mark ${character.name} as dead? This action cannot be undone.`,
      )
    ) {
      try {
        await CharacterService.letCharacterDie(character_id);
        alert(`${character.name} has been marked as dead.`);
        const updatedCharacter =
          await CharacterService.getCharacterById(character_id);
        setCharacter(updatedCharacter);
      } catch (error) {
        alert(`Failed to let character die: ${error.message}`);
      }
    }
  };

  const handleDelete = async () => {
    if (
      confirm(
        `Are you sure you want to delete ${character.name}? This action cannot be undone.`,
      )
    ) {
      try {
        await CharacterService.deleteCharacter(character.id);
        router.push("/characters");
      } catch (error) {
        alert(`Failed to delete character: ${error.message}`);
      }
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
