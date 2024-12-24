import React, { useState, useEffect, useRef } from "react";

import {
  fetchCharacterClasses,
  fetchCharacterRaces,
  fetchCharacterAlignments,
} from "@/service/EnumService";

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
  const [characterRaces, setCharacterRaces] = useState([]);
  const [characterAlignments, setCharacterAlignments] = useState([]);
  const [selectedClass, setSelectedClass] = useState(null);
  const [selectedRace, setSelectedRace] = useState(null);
  const [selectedAlignment, setSelectedAlignment] = useState(null);

  const customStyles = {
    control: (provided) => ({
      ...provided,
      minWidth: "300px",
      height: "40px",
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

  const formatLabel = (value) => {
    return value
      .replace(/_/g, " ")
      .replace(/7/g, "/")
      .toLowerCase()
      .replace(/(^|[ /])[a-z]/g, (match) => match.toUpperCase());
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [classesData, racesData, alignmentsData] = await Promise.all([
          fetchCharacterClasses(),
          fetchCharacterRaces(),
          fetchCharacterAlignments(),
        ]);

        setCharacterClasses(
          classesData.map((cls) => ({ value: cls, label: formatLabel(cls) })),
        );
        setCharacterRaces(
          racesData.map((race) => ({ value: race, label: formatLabel(race) })),
        );
        setCharacterAlignments(
          alignmentsData.map((alignment) => ({
            value: alignment,
            label: formatLabel(alignment),
          })),
        );
      } catch (error) {
        console.error("Error fetching character data", error);
      }
    };

    fetchData();
  }, []);

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const formData = {
      name: name,
      characterClass: selectedClass ? selectedClass.value : null,
      race: selectedRace ? selectedRace.value : null,
      alignment: selectedAlignment ? selectedAlignment.value : null,
    };
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleFormSubmit} className="d-flex flex-column gap-4">
      <input
        type="text"
        className="custom-input"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <div className="d-flex align-items-center justify-content-end gap-2">
        <label htmlFor="raceSelect">Race:</label>
        <Select
          id="raceSelect"
          options={characterRaces}
          value={selectedRace}
          onChange={setSelectedRace}
          isClearable
          placeholder="Select a race..."
          styles={customStyles}
        />
      </div>

      <div className="d-flex align-items-center justify-content-end gap-2">
        <label htmlFor="classSelect">Class:</label>
        <Select
          id="classSelect"
          options={characterClasses}
          value={selectedClass}
          onChange={setSelectedClass}
          isClearable
          placeholder="Select a class..."
          styles={customStyles}
        />
      </div>

      <div className="d-flex align-items-center justify-content-end gap-2">
        <label htmlFor="alignmentSelect">Alignment:</label>
        <Select
          id="alignmentSelect"
          options={characterAlignments}
          value={selectedAlignment}
          onChange={setSelectedAlignment}
          isClearable
          placeholder="Select an alignment..."
          styles={customStyles}
        />
      </div>

      <div className="d-flex align-items-center justify-content-end gap-2">
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
        <div className="d-flex flex-grow-1" />
        <Button variant="action" type="submit" label="Create Character" />
      </ButtonRow>
    </form>
  );
}
