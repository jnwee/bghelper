import ApiService from "../ApiService";

class CharacterFilterService {
  async getCharactersByProgress(progress = "BG1") {
    return ApiService.get(`characters/filter?progress=${progress}`);
  }

  async getCharactersByStatus(status = "ALIVE") {
    return ApiService.get(`characters/filter?status=${status}`);
  }

  async getCharacterStats() {
    return ApiService.get("characters/stats");
  }
}

export default new CharacterFilterService();
