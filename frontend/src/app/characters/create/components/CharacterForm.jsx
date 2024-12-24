import React, { useState, useEffect, useRef } from "react";

import { fetchCharacterClasses } from "@/service/EnumService";

import dynamic from "next/dynamic";
const Select = dynamic(() => import("react-select"), { ssr: false });

import ButtonRow from "@/components/container/ButtonRow";
import Button from "@/components/Button";

export default function CharacterForm({
  name,
  setName,
  onImageChange,
  onSubmit,
}) {
  const [characterClasses, setCharacterClasses] = useState([]);
  const [selectedClass, setSelectedClass] = useState(null);

  const customStyles = {
    control: (provided) => ({
      ...provided,
      width: "335px",
      borderRadius: "8px",
      border: "2px solid",
      borderColor: "var(--button-hover)",
      boxShadow: "none",
      backgroundColor: "var(--background)",
      "&:hover": {
        borderColor: "var(--text-color)",
      },
    }),
    singleValue: (provided) => ({
      ...provided,
      color: "var(--text-color)", // Text color of the selected item
      fontWeight: "500",
    }),
    option: (provided, state) => ({
      ...provided,
      backgroundColor: state.isSelected
        ? "var(--button-hover)"
        : state.isFocused
          ? "var(--button-hover)"
          : "var(--background)",
      color: state.isSelected
        ? "var(--background)"
        : state.isFocused
          ? "var(--background)"
          : "var(--text-color)",
      padding: 10,
    }),
    menu: (provided) => ({
      ...provided,
      margin: 0,
      backgroundColor: "var(--background)", // Background color for the entire menu
    }),
    menuList: (provided) => ({
      ...provided,
      padding: 0,
      backgroundColor: "var(--background)", // Removes white bars at top/bottom
    }),
    dropdownIndicator: (provided) => ({
      ...provided,
      color: "var(--text-color)", // Color of the dropdown arrow
    }),
    clearIndicator: (provided) => ({
      ...provided,
      color: "var(--text-color)", // Color of the clear (x) icon
      "&:hover": {
        color: "var(--danger-color)",
      },
    }),
    indicatorSeparator: (provided) => ({
      ...provided,
      display: "none",
    }),
  };

  useEffect(() => {
    const fetchClasses = async () => {
      try {
        const data = await fetchCharacterClasses();
        const options = data.map((cls) => ({
          value: cls,
          label: cls
            .replace(/_/g, " ")
            .replace(/7/g, "/")
            .toLowerCase()
            .replace(/(^|[ /])[a-z]/g, (match) => match.toUpperCase()),
        }));
        setCharacterClasses(options);
      } catch (error) {
        console.error("Error fetching character classes", error);
      }
    };

    fetchClasses();
  }, []);

  const handleClassChange = (selectedOption) => {
    setSelectedClass(selectedOption);
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const formData = {
      name: name,
      characterClass: selectedClass ? selectedClass.value : null, // Send the value to the backend
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

      {/* Test React-Select */}
      <div className="d-flex align-items-center gap-2">
        <label htmlFor="classSelect">Class:</label>
        <Select
          id="classSelect"
          options={characterClasses}
          value={selectedClass}
          onChange={handleClassChange}
          isClearable
          placeholder="Type or select a class..."
          styles={customStyles}
          classNamePrefix="custom-select"
        />
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
