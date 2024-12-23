import ApiService from "../ApiService";

class CharacterService {
  async getCharacterById(id) {
    return ApiService.get(`characters/${id}`);
  }

  async addCharacter(character) {
    return ApiService.post("characters", character);
  }

  async letCharacterDie(id) {
    return ApiService.patch(`characters/${id}/die`);
  }

  async advanceCharacter(id) {
    return ApiService.patch(`characters/${id}/advance`);
  }

  async deleteCharacter(id) {
    return ApiService.delete(`characters/${id}`);
  }
}

export default new CharacterService();
