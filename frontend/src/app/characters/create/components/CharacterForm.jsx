import React, { useState, useEffect, useRef } from "react";

import { fetchCharacterClasses } from "@/service/EnumService";

import ButtonRow from "@/components/container/ButtonRow";
import Button from "@/components/Button";

export default function CharacterForm({
  name,
  setName,
  onImageChange,
  onSubmit,
}) {
  const [characterClasses, setCharacterClasses] = useState([]);
  const [filteredClasses, setFilteredClasses] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedClass, setSelectedClass] = useState(null);
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const inputRef = useRef(null);

  useEffect(() => {
    const fetchClasses = async () => {
      try {
        const data = await fetchCharacterClasses();
        setCharacterClasses(data);
        setFilteredClasses(data);
      } catch (error) {
        console.error("Error fetching character classes", error);
      }
    };

    fetchClasses();
  }, []);

  const formatClassName = (cls) => {
    return cls
      .replace(/_/g, " ")
      .replace(/7/g, "/")
      .toLowerCase()
      .replace(/(^|[ /])[a-z]/g, (match) => match.toUpperCase());
  };

  const handleSearch = (e) => {
    const term = e.target.value;
    setSearchTerm(term);
    if (term === "") {
      setSelectedClass(null);
    }
    const filtered = characterClasses.filter((cls) =>
      formatClassName(cls).toLowerCase().includes(term.toLowerCase()),
    );
    setFilteredClasses(filtered);
    setDropdownVisible(true);
  };

  const handleSelect = (cls) => {
    setSearchTerm(formatClassName(cls));
    setSelectedClass(cls);
    setDropdownVisible(false);
  };

  const handleFocus = () => {
    setDropdownVisible(true);
    setFilteredClasses(characterClasses);
  };

  const handleBlur = (e) => {
    if (
      !e.relatedTarget ||
      !e.relatedTarget.classList.contains("dropdown-item")
    ) {
      if (!characterClasses.includes(selectedClass)) {
        setSearchTerm(""); // Reset input if invalid selection
        setSelectedClass(null);
      }
      setDropdownVisible(false);
    }
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const formData = {
      name: name,
      characterClass: selectedClass, // Ensure correct key for backend
    };
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleFormSubmit} className="d-flex flex-column gap-3">
      {/* Name Input */}
      <input
        type="text"
        className="custom-input"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />

      {/* Class Select */}
      <div className="d-flex align-items-center gap-2 position-relative">
        <label htmlFor="classSelect">Class:</label>
        <input
          type="text"
          id="classSelect"
          className="custom-select"
          placeholder="Type to filter..."
          value={searchTerm}
          onChange={handleSearch}
          onFocus={handleFocus}
          onBlur={handleBlur}
          ref={inputRef}
        />
        {dropdownVisible && (
          <div
            className="dropdown-menu show w-100 custom-dropdown"
            style={{
              maxHeight: "500px",
              overflowY: "auto",
              position: "absolute",
              top: "100%",
            }}
          >
            {filteredClasses.map((cls) => (
              <div
                key={cls}
                className="dropdown-item custom-dropdown-item"
                onMouseDown={() => handleSelect(cls)}
              >
                {formatClassName(cls)}
              </div>
            ))}
          </div>
        )}
      </div>

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
        <Button
          variant="link"
          href="/characters"
          iconClass="bi-arrow-left"
          label="Back"
        />
        <Button variant="action" type="submit" label="Create Character" />
      </ButtonRow>
    </form>
  );
}
