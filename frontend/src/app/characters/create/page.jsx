"use client";

import React, { useState } from "react";
import CharacterService from "@/service/CharacterService";
import "@/app/css/create_character.css";

export default function CreateCharacterPage() {
  const [name, setName] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [message, setMessage] = useState("");

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
      if (!name || !imageFile) {
        throw new Error("Name and Portrait are required!");
      }

      const reader = new FileReader();
      reader.onloadend = async () => {
        const payload = { name, dead: false, imageUrl: reader.result };
        await CharacterService.addCharacter(payload);

        setMessage("Character created successfully! ⚔️");
        setName("");
        setImageFile(null);
        setPreviewUrl(null);
      };
      reader.readAsDataURL(imageFile);
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
                required
              />
              <label htmlFor="image" className="file-button">
                <i className="bi bi-upload"></i>
              </label>
            </div>

            {/* Create Button */}
            <button type="submit" className="custom-button">
              Create Character
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
