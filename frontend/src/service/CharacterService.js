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
    return this.request(endpoint, "PATCH", data);
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

  async getCharactersByStatus(status) {
    if (!status) {
      status = "ALIVE";
    }
    return this.get("characters/filter?status=" + status);
  }

  async getCharacterById(id) {
    return this.get("characters/" + id);
  }

  async addCharacter(character) {
    return this.post("characters", character);
  }

  async letCharacterDie(id) {
    return this.patch("characters/" + id + "/die");
  }

  async uploadImage(characterId, formData) {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/characters/${characterId}/upload`,
      {
        method: "POST",
        body: formData, // FormData ensures multipart/form-data is used
      },
    );

    if (!response.ok) {
      throw new Error(`Failed to upload image: ${response.statusText}`);
    }
  }

  getCharacterImage(id) {
    return this.baseUrl + "/characters/" + id + "/image";
  }

  async deleteCharacter(id) {
    return this.delete("characters/" + id);
  }
}

export default new CharacterService();
