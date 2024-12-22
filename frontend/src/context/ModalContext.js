// context/ModalContext.js
"use client";

import React, { createContext, useContext, useState } from "react";
import Modal from "@/components/Modal";

const ModalContext = createContext();

// Custom hook to access modal context
export const useModal = () => useContext(ModalContext);

export const ModalProvider = ({ children }) => {
  const [modal, setModal] = useState({
    show: false,
    title: "",
    content: null,
    onConfirm: null,
  });

  // Show modal with dynamic content
  const showModal = (title, content, onConfirm) => {
    setModal({
      show: true,
      title,
      content,
      onConfirm,
    });
  };

  // Hide modal
  const hideModal = () => {
    setModal({ ...modal, show: false });
  };

  // Confirm and close
  const handleConfirm = () => {
    if (modal.onConfirm) modal.onConfirm();
    hideModal();
  };

  return (
    <ModalContext.Provider value={{ showModal, hideModal }}>
      {children}

      {/* Global Modal */}
      <Modal
        show={modal.show}
        title={modal.title}
        onClose={hideModal}
        onConfirm={handleConfirm}
      >
        {modal.content}
      </Modal>
    </ModalContext.Provider>
  );
};
