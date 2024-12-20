import React from "react";
import ActionButton from "@/components/ActionButton";
import ButtonRow from "@/components/ButtonRow";
import LinkButton from "@/components/LinkButton";

export default function CharacterForm({
  name,
  setName,
  onImageChange,
  onSubmit,
}) {
  return (
    <form onSubmit={onSubmit} className="d-flex flex-column gap-3">
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
          onChange={onImageChange}
        />
        <label htmlFor="image" className="file-button">
          <i className="bi bi-upload"></i>
        </label>
      </div>

      <ButtonRow>
        {/* Submit Button */}
        <LinkButton href="/characters" iconClass="bi-arrow-left" label="Back" />
        <ActionButton type="submit" label="Create Character" className="" />
      </ButtonRow>
    </form>
  );
}
