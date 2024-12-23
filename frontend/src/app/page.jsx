"use client";

import React, { useState, useEffect } from "react";

import Header from "@/components/Header";
import ButtonRow from "@/components/container/ButtonRow";
import AsciiArt from "./components/AsciiArt";
import PageContainer from "@/components/container/PageContainer";
import CircleChart from "./components/CircleChart";
import CharacterFilterService from "@/service/characters/CharacterFilterService";
import Button from "@/components/Button";

export default function Home() {
  const [stats, setStats] = useState([]);
  const [showStatistics, setShowStatistics] = useState(false);

  const labels = [
    "Baldur's Gate",
    "Shadows of Amn",
    "Throne of Bhaal",
    "Ascended",
  ];
  const colors = ["#8D0B41", "#D39D55", "#D6CFB4", "#EEE7D5"];

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const data = await CharacterFilterService.getCharacterStats();
        setStats(data);
      } catch (error) {
        console.error("Failed to fetch stats:", error);
      }
    };
    fetchStats();
  }, []);

  return (
    <PageContainer>
      <Header
        title="A BALDUR'S GATE COMPANION"
        useH2={false}
        leadText="Keep track of your no-reload runs"
        leadFontSize="1.5rem"
      />
      <ButtonRow>
        <Button
          variant="link"
          href="/characters"
          iconClass="bi-arrow-right"
          label="View Characters"
        />
        <Button
          variant="link"
          href="/characters/create"
          iconClass="bi-plus-circle"
          label="Add Character"
        />
        <div className="d-flex flex-grow-1" />
        <Button
          variant="toggle"
          inactiveText="Statistics"
          activeText="Ascii"
          isToggled={showStatistics}
          onToggle={() => setShowStatistics(!showStatistics)}
          iconClass="bi bi-arrow-repeat"
        />
      </ButtonRow>
      {!showStatistics ? (
        <>
          <AsciiArt scale={0.9} />
        </>
      ) : (
        <>
          <CircleChart data={stats} labels={labels} colors={colors} />
        </>
      )}
    </PageContainer>
  );
}
