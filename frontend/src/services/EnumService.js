import ApiService from "./ApiService";

export async function fetchCharacterClasses() {
  const response = ApiService.get("enums/classes");

  return await response;
}

export async function fetchCharacterRaces() {
  const response = ApiService.get("enums/races");

  return await response;
}

export async function fetchCharacterAlignments() {
  const response = ApiService.get("enums/alignments");

  return await response;
}

export async function fetchCompanionsByGame(gameVersion) {
  if (!gameVersion) {
    gameVersion = "bg1";
  }

  const response = ApiService.get("enums/companions/" + gameVersion);

  return await response;
}
