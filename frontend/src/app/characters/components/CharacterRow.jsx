import React, { useState, useEffect, useRef } from "react";
import CharacterCard from "./CharacterCard";
import "./css/character_row.css";

export default function CharacterRow({ characters }) {
  const rowRef = useRef(null);
  const [scrollPosition, setScrollPosition] = useState(0);
  const [maxScroll, setMaxScroll] = useState(0);
  const [isScrollable, setIsScrollable] = useState(false);
  const cardWidth = 200;
  const gap = 16;

  // Update max scroll and detect if scrolling is needed
  useEffect(() => {
    if (rowRef.current) {
      const fullScrollWidth = rowRef.current.scrollWidth;
      const visibleWidth = rowRef.current.clientWidth;
      setMaxScroll(fullScrollWidth - visibleWidth);
      setIsScrollable(fullScrollWidth > visibleWidth);
    }
  }, [characters]);

  // Handle slider change
  const handleSliderChange = (e) => {
    const newScrollPosition = (e.target.value / 100) * maxScroll;
    setScrollPosition(newScrollPosition);
    rowRef.current.scrollTo({ left: newScrollPosition, behavior: "smooth" });
  };

  // Move 5 cards to the right
  const handleNext = () => {
    const newScrollPosition = Math.min(
      scrollPosition + (cardWidth + gap) * 5,
      maxScroll,
    );
    setScrollPosition(newScrollPosition);
    rowRef.current.scrollTo({ left: newScrollPosition, behavior: "smooth" });
  };

  // Move 5 cards to the left
  const handlePrevious = () => {
    const newScrollPosition = Math.max(
      scrollPosition - (cardWidth + gap) * 5,
      0,
    );
    setScrollPosition(newScrollPosition);
    rowRef.current.scrollTo({ left: newScrollPosition, behavior: "smooth" });
  };

  return (
    <div className="character-row-container position-relative my-1">
      {/* Left Navigation Button */}
      {isScrollable && scrollPosition > 0 && (
        <button className="btn-navigate btn-previous" onClick={handlePrevious}>
          <i className="bi bi-arrow-bar-left"></i>
        </button>
      )}

      {/* Character Row */}
      <div
        className="character-row d-flex p-2"
        ref={rowRef}
        style={{
          overflowX: "hidden",
          scrollBehavior: "smooth",
          whiteSpace: "nowrap",
        }}
      >
        {characters.map((character) => (
          <div key={character.id} className="character-card-wrapper">
            <CharacterCard
              id={character.id}
              name={character.name}
              status={character.status}
            />
          </div>
        ))}
      </div>

      {/* Right Navigation Button */}
      {isScrollable && scrollPosition < maxScroll && (
        <button className="btn-navigate btn-next" onClick={handleNext}>
          <i className="bi bi-arrow-bar-right"></i>
        </button>
      )}

      {/* Slider (only appears if needed) */}
      {isScrollable && (
        <input
          type="range"
          className="character-row-slider mt-1"
          min="0"
          max="100"
          step="1"
          value={(scrollPosition / maxScroll) * 100 || 0}
          onChange={handleSliderChange}
        />
      )}
    </div>
  );
}
