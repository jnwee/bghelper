import React from "react";

export default function CharacterPreview({ previewUrl, name }) {
  return (
    <div className="preview-container d-flex flex-column align-items-center">
      {previewUrl ? (
        <img src={previewUrl} alt="Preview" className="preview-image" />
      ) : (
        <div className="preview-placeholder">No Image</div>
      )}
    </div>
  );
}
