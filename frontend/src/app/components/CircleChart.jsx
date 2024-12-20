import React from "react";
import "./circle_chart.css";

export default function CircleChart({ data, labels, colors }) {
  const total = data.reduce((sum, value) => sum + value, 0);

  // Calculate percentages and offsets
  const percentages = data.map((value) => (value / total) * 100);
  const gradientStops = percentages.reduce((acc, percent, index) => {
    const previousStop = acc.length > 0 ? acc[acc.length - 1].stop : 0;
    const newStop = previousStop + percent;
    acc.push({ color: colors[index], stop: newStop });
    return acc;
  }, []);

  // Generate conic-gradient CSS value
  const conicGradient = gradientStops
    .map(
      ({ color, stop }, index) =>
        `${color} ${stop - percentages[index]}% ${stop}%`,
    )
    .join(", ");

  return (
    <div className="circle-chart-wrapper">
      {/* Chart */}
      <div
        className="circle-chart"
        style={{
          background: `conic-gradient(${conicGradient})`,
        }}
      ></div>

      {/* Labels */}
      <div className="chart-labels">
        {labels.map((label, index) => (
          <div key={index} className="chart-label">
            <span
              className="label-color"
              style={{ backgroundColor: colors[index] }}
            ></span>
            {label}
          </div>
        ))}
      </div>
    </div>
  );
}
