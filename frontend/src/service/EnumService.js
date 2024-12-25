import ApiService from "./ApiService";

export async function fetchCharacterClasses() {
  const response = ApiService.get("enums/classes");

  return await response;
}

export async function fetchCharacterRaces() {
  const response = ApiService.get("enums/races");

  /*if (!response.ok) {
    throw new Error(`Failed to fetch races list: ${response.statusText}`);
    } idk why this doesn't work */

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
  // if (!response.ok) {
  //   throw new Error("Failed to fetch " + gameVersion + " companions");
  // }
  return await response;
}
