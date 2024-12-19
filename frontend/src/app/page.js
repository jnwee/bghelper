"use client";

import LinkButton from "@/components/LinkButton";
import Header from "@/components/Header";
import ButtonRow from "@/components/ButtonRow";
import AsciiArt from "./components/AsciiArt";
import PageContainer from "@/components/PageContainer";

export default function Home() {
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
      </ButtonRow>
      <AsciiArt scale={0.9} />
    </PageContainer>
  );
}
