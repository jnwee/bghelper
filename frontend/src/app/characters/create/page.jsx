"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";

import CharacterService from "@/service/characters/CharacterService";
import ImageService from "@/service/ImageService";

import "./create_character.css";
import PageContainer from "@/components/container/PageContainer";
import Header from "@/components/Header";
import Column from "@/components/container/Column";
import CharacterForm from "./components/CharacterForm";
import CharacterPreview from "./components/CharacterPreview";

export default function CreateCharacterPage() {
  const [name, setName] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);

  const router = useRouter();

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

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (!name) {
        throw new Error("Name is required!");
      }

      const createdCharacter = await CharacterService.addCharacter({
        name,
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

      <div className="row h-100">
        {/* Left Column: Preview */}
        <Column colSize="col-md-6">
          <CharacterPreview previewUrl={previewUrl} name={name} />
        </Column>

        {/* Right Column: Form */}
        <Column colSize="col-md-6">
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
