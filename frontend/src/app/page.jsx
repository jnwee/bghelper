"use client";

import React, { useState, useEffect } from "react";
import { useNotification } from "@/context/NotificationContext/NotificationContext";

import CharacterFilterService from "@/services/characters/CharacterFilterService";

import Header from "@/components/Header";
import ButtonRow from "@/components/container/ButtonRow";
import PageContainer from "@/components/container/PageContainer";
import Button from "@/components/Button";

import CircleChart from "./components/CircleChart";
import AsciiArt from "./components/AsciiArt";

export default function Home() {
  const [stats, setStats] = useState([]);
  const [showStatistics, setShowStatistics] = useState(false);
  const { showNotification } = useNotification();

  const labels = [
    "Baldur's Gate",
    "Shadows of Amn",
    "Throne of Bhaal",
    "Ascended",
  ];
  const chartColors = ["#8D0B41", "#D39D55", "#D6CFB4", "#EEE7D5"];

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const data = await CharacterFilterService.getCharacterStats();
        setStats(data);
      } catch (error) {
        showNotification(
          error.message || error.status + "Failed to fetch stats",
          "danger",
        );
      }
    };
    fetchStats();
  }, []);

  return (
    <PageContainer>
      <Header
        title="A BALDUR'S GATE COMPANION"
        level={1}
        leadText="Keep track of your no-reload runs"
        leadFontSize="1.5rem"
      />
      <ButtonRow leftCount={2}>
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
        <Button
          variant="toggle"
          inactiveText="Statistics"
          activeText="Ascii"
          isToggled={showStatistics}
          onClick={() => setShowStatistics(!showStatistics)}
          iconClass="bi bi-arrow-repeat"
        />
      </ButtonRow>
      {!showStatistics ? (
        <>
          <AsciiArt scale={0.9} />
        </>
      ) : (
        <>
          <CircleChart data={stats} labels={labels} colors={chartColors} />
        </>
      )}
    </PageContainer>
  );
}
