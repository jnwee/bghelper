const BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8089/api";

class CharacterService {
  constructor() {
    this.baseUrl = BASE_URL;
  }

  async get(endpoint) {
    return this.request(endpoint, "GET");
  }

  async post(endpoint, data) {
    return this.request(endpoint, "POST", data);
  }

  async patch(endpoint, data) {
    return this.request(endpoint, "PUT", data);
  }

  async put(endpoint, data) {
    return this.request(endpoint, "PUT", data);
  }

  async delete(endpoint) {
    return this.request(endpoint, "DELETE");
  }

  async request(endpoint, method, data = null) {
    try {
      const response = await fetch(`${this.baseUrl}/${endpoint}`, {
        method: method,
        headers: {
          "Content-Type": "application/json",
        },
        body: data ? JSON.stringify(data) : null,
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.status} ${response.statusText}`);
      }

      return await response.json();
    } catch (error) {
      console.error(
        `API ${method} Request to ${endpoint} failed:`,
        error.message,
      );
      throw error; // Rethrow for caller handling
    }
  }

  // Specific Methods for Characters
  async getAllCharacters() {
    return this.get("characters");
  }

  async getLightweightCharacters() {
    return this.get("characters/lightweight");
  }

  async getCharacterById(id) {
    return this.get("characters/" + id);
  }

  async addCharacter(character) {
    return this.post("characters", character);
  }

  async letCharacterDie(id) {
    return this.post("characters/" + id + "/die");
  }
}

export default new CharacterService();
