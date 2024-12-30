import React from "react";
import { PieChart, Pie, Cell, Legend, ResponsiveContainer } from "recharts";

export default function CircleChart({ data, labels, colors }) {
  const chartData = data.map((value, index) => ({
    name: labels[index],
    value: value,
  }));

  return (
    <div style={{ width: "100%", height: "70vh" }}>
      <ResponsiveContainer width="100%" height="100%">
        <PieChart>
          <Pie
            data={chartData}
            cx="50%"
            cy="50%"
            outerRadius="90%"
            fill="#8884d8"
            dataKey="value"
            stroke="none"
          >
            {data.map((_, index) => (
              <Cell
                key={`cell-${index}`}
                fill={colors[index % colors.length]}
              />
            ))}
          </Pie>
          <Legend
            verticalAlign="bottom"
            height={36}
            iconType="circle"
            formatter={(value, entry) => <span>{entry.payload.name}</span>}
          />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
}
