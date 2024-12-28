"use client";

import React from "react";
import "./modal_dialog.css";
import Button from "@/components/Button";

export default function Modal({ show, title, children, onClose, onConfirm }) {
  if (!show) return null;

  return (
    <div className="modal-backdrop">
      <div className="modal-content">
        <div className="d-flex justify-content-between align-items-center">
          <h5 className="m-0">{title}</h5>
          <Button variant="close" onClick={onClose} />
        </div>

        <div className="p-1">{children}</div>

        <div className="d-flex justify-content-between align-items-center">
          <Button variant="link" label={"Cancel"} onClick={onClose} />
          <Button variant="action" label={"Confirm"} onClick={onConfirm} />
        </div>
      </div>
    </div>
  );
}
