"use client"; // This ensures it's treated as a client component

import React from "react";
import "./css/modal_dialog.css";
import "./css/buttons.css";

export default function Modal({ show, title, children, onClose, onConfirm }) {
  if (!show) return null;

  return (
    <div className="modal-backdrop">
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title">{title}</h5>
          <button className="close-btn" onClick={onClose}>
            &times;
          </button>
        </div>

        <div className="modal-body">{children}</div>

        <div className="modal-footer">
          <button className="link-button" onClick={onClose}>
            Cancel
          </button>
          <button className="action-button" onClick={onConfirm}>
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
}
