"use client";

import LinkButton from "@/components/LinkButton";
import Header from "@/components/Header";
import ButtonRow from "@/components/ButtonRow";
import AsciiArt from "./components/AsciiArt";
import PageContainer from "@/components/PageContainer";
import FlexGrow from "@/components/FlexGrow";
import ToggleButton from "@/components/ToggleButton";
import React, { useState, useEffect } from "react";
import CircleChart from "./components/CircleChart";
import CharacterService from "@/service/CharacterService";

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
        const data = await CharacterService.getCharacterStats();
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
        <LinkButton
          href="/characters"
          iconClass="bi-arrow-right"
          label="View Characters"
        />
        <LinkButton
          href="/characters/create"
          iconClass="bi-plus-circle"
          label="Add Character"
        />
        <FlexGrow />
        <ToggleButton
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
