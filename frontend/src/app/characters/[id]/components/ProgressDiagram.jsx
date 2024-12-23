import React from "react";
import Button from "@/components/Button"; // Reuse existing button
import "./flowchart.css"; // Custom CSS for styling

export default function ProgressDiagram({ currentStep, onAdvance }) {
  const steps = [
    { id: 1, name: "Baldur's Gate" },
    { id: 2, name: "Shadows of Amn" },
    { id: 3, name: "Throne of Bhaal" },
    { id: 4, name: "Ascension" },
  ];

  // Positions for SVG elements
  const positions = steps.map((_, index) => ({
    x: 150,
    y: 100 + index * 100,
  }));

  return (
    <div style={{ textAlign: "center", marginTop: "0px" }}>
      <svg width="300" height="500">
        {/* Arrows Connecting Steps */}
        {positions.map((pos, index) => {
          if (index === 0) return null; // Skip first element (no arrow above it)

          const prevPos = positions[index - 1];
          return (
            <path
              key={index}
              d={`M ${prevPos.x} ${prevPos.y + 40} C ${prevPos.x} ${
                prevPos.y + 70
              }, ${pos.x} ${pos.y - 30}, ${pos.x} ${pos.y}`}
              stroke="var(--text-color)"
              fill="transparent"
              strokeWidth="3"
            />
          );
        })}

        {/* Steps and Buttons */}
        {steps.map((step, index) => {
          const isActive = index + 1 === currentStep;
          const isCompleted = index + 1 < currentStep;
          const pos = positions[index];

          return (
            <g key={step.id}>
              <rect
                x={pos.x - 100}
                y={pos.y}
                width="200"
                height="50"
                className={
                  isCompleted ? "completed" : isActive ? "active" : "inactive"
                }
              />
              <text
                x={pos.x}
                y={pos.y + 25}
                textAnchor="middle"
                fill={isCompleted ? "var(--text-color)" : "var(--background)"}
              >
                {step.name}
              </text>

              {/* Render ActionButton for the current step */}
              {isActive && (
                <foreignObject
                  x={pos.x - 100}
                  y={pos.y - 25}
                  width="200"
                  height="80"
                >
                  <Button
                    variant="action"
                    label={step.name}
                    onClick={onAdvance}
                    iconClass="bi-arrow-down"
                  />
                </foreignObject>
              )}
            </g>
          );
        })}
      </svg>
    </div>
  );
}
