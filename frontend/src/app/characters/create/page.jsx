"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import { useNotification } from "@/context/NotificationContext";

import CharacterService from "@/service/characters/CharacterService";
import ImageService from "@/service/ImageService";

import "./create_character.css";
import PageContainer from "@/components/container/PageContainer";
import Header from "@/components/Header";
import Column from "@/components/container/Column";
import CharacterForm from "./components/CharacterForm";
import CharacterPreview from "./components/CharacterPreview";

export default function CreateCharacterPage() {
  const router = useRouter();
  const { showNotification } = useNotification();

  const [name, setName] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImageFile(file);

      // Generate preview
      const reader = new FileReader();
      reader.onloadend = () => setPreviewUrl(reader.result);
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = async (formData) => {
    try {
      if (!formData.name || formData.name.trim() === "") {
        showNotification("Character's name can't be empty", "danger");
        return;
      }

      if (!formData.race || formData.race.trim() === "") {
        showNotification("Character's race can't be empty", "danger");
        return;
      }

      if (!formData.characterClass || formData.characterClass.trim() === "") {
        showNotification("Character's class/kit can't be empty", "danger");
        return;
      }

      if (!formData.alignment || formData.alignment.trim() === "") {
        showNotification("Character's alignment can't be empty", "danger");
        return;
      }

      const createdCharacter = await CharacterService.addCharacter({
        name: formData.name,
        race: formData.race,
        characterClass: formData.characterClass,
        alignment: formData.alignment,
      });

      if (!createdCharacter.id) {
        throw new Error("Character creation failed. No ID returned.");
      }

      if (imageFile) {
        const formData = new FormData();
        formData.append("image", imageFile);
        await ImageService.uploadCharacterImage(createdCharacter.id, formData);
      }
      setName("");
      setImageFile(null);
      setPreviewUrl(null);
      router.push("/characters");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <PageContainer>
      <Header title="Create Character" useH2={false} />

      <div className="row h-100 mt-5">
        {/* Left Column: Preview */}
        <Column colSize="col-6">
          <CharacterPreview previewUrl={previewUrl} name={name} />
        </Column>

        {/* Right Column: Form */}
        <Column colSize="col-6">
          <CharacterForm
            name={name}
            setName={setName}
            onImageChange={handleImageChange}
            onSubmit={handleSubmit}
          />
        </Column>
      </div>
    </PageContainer>
  );
}
