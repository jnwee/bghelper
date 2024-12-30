"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import { useNotification } from "@/context/NotificationContext/NotificationContext";

import CharacterService from "@/services/characters/CharacterService";
import ImageService from "@/services/ImageService";

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
      if (file.size > 5000000) {
        showNotification("File is too large (max 5MB).", "danger");
        return;
      }

      setImageFile(file);

      // Generate preview
      const reader = new FileReader();
      reader.onloadend = () => setPreviewUrl(reader.result);
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = async (formData) => {
    try {
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
      router.push("/characters/" + createdCharacter.id);
    } catch (error) {
      showNotification(error.message);
    }
  };

  return (
    <PageContainer>
      <Header title={name ? name : "Create Character"} useH2={false} />

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
