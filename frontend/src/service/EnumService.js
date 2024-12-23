import ApiService from "./ApiService";

export async function fetchCharacterClasses() {
  const response = ApiService.get("enums/classes");
  return await response;
}
