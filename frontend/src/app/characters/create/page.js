"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import CharacterService from "@/service/CharacterService";
import Column from "@/components/Column";
import Row from "@/components/Row";
import "./create_character.css";
import "@/app/css/buttons.css";
import PageContainer from "@/components/PageContainer";
import Header from "@/components/Header";
import Message from "@/components/Message";
import CharacterForm from "./components/CharacterForm";
import CharacterPreview from "./components/CharacterPreview";

export default function CreateCharacterPage() {
  const [name, setName] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [message, setMessage] = useState("");

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
    setMessage("");

    try {
      if (!name) {
        throw new Error("Name is required!");
      }

      // Step 1: Create the character
      const createdCharacter = await CharacterService.addCharacter({
        name,
      });

      if (!createdCharacter.id) {
        throw new Error("Character creation failed. No ID returned.");
      }

      // Step 2: Upload the image
      if (imageFile) {
        const formData = new FormData();
        formData.append("image", imageFile);
        await CharacterService.uploadImage(createdCharacter.id, formData);
      }
      setName("");
      setImageFile(null);
      setPreviewUrl(null);
      router.push("/characters");
    } catch (error) {
      console.error(error);
      setMessage(`Error: ${error.message}`);
    }
  };

  return (
    <PageContainer>
      <Header title="Create Character" useH2={false} />
      <Message
        text={message}
        type={message.startsWith("Error") ? "error" : "success"}
      />

      <Row>
        {/* Left Column: Preview */}
        <Column className="justify-content-center d-flex">
          <CharacterPreview previewUrl={previewUrl} name={name} />
        </Column>

        {/* Right Column: Form */}
        <Column className="justify-content-center flex">
          <CharacterForm
            name={name}
            setName={setName}
            onImageChange={handleImageChange}
            onSubmit={handleSubmit}
          />
        </Column>
      </Row>
    </PageContainer>
  );
}
