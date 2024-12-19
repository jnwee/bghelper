"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import CharacterService from "@/service/CharacterService";
import "./create_character.css";
import "@/app/css/buttons.css";

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
    <div className="container mt-5">
      <h1 className="mb-4 text-center">Create a New Character</h1>
      {message && (
        <div
          className={`alert ${
            message.startsWith("Error") ? "alert-danger" : "alert-success"
          }`}
        >
          {message}
        </div>
      )}

      {/* Two Column Layout */}
      <div className="row g-4">
        {/* Left Column: Preview */}
        <div className="col-md-6 d-flex flex-column align-items-center">
          <div className="preview-container">
            {previewUrl ? (
              <img src={previewUrl} alt="Preview" className="preview-image" />
            ) : (
              <div className="preview-placeholder">No Image</div>
            )}
            <div className="preview-name">{name || "Name"}</div>
          </div>
        </div>

        {/* Right Column: Form */}
        <div className="col-md-6">
          <form onSubmit={handleSubmit} className="d-flex flex-column gap-3">
            {/* Name Input */}
            <input
              type="text"
              className="custom-input"
              placeholder="Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />

            {/* File Input */}
            <div className="d-flex align-items-center gap-2">
              <label htmlFor="image" className="portrait-label">
                Portrait:
              </label>
              <input
                type="file"
                id="image"
                className="file-input"
                accept="image/*"
                onChange={handleImageChange}
              />
              <label htmlFor="image" className="file-button">
                <i className="bi bi-upload"></i>
              </label>
            </div>

            {/* Create Button */}
            <button type="submit" className="action-button">
              Create Character
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
