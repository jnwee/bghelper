// ApiService.js
const BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8089/api";

class ApiService {
  static async request(endpoint, method, data = null) {
    try {
      const response = await fetch(`${BASE_URL}/${endpoint}`, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: data ? JSON.stringify(data) : null,
      });

      if (!response.ok) {
        const errorBody = await response.text();
        throw new Error(`${response.status}: ${errorBody}`);
      }

      if (response.headers.get("Content-Type")?.includes("application/json")) {
        return await response.json();
      }
      return await response.text();
    } catch (error) {
      console.error(`API ${method} Request to ${endpoint} failed:`, error);
      throw error;
    }
  }

  static get(endpoint) {
    return this.request(endpoint, "GET");
  }

  static post(endpoint, data) {
    return this.request(endpoint, "POST", data);
  }

  static patch(endpoint, data) {
    return this.request(endpoint, "PATCH", data);
  }

  static delete(endpoint) {
    return this.request(endpoint, "DELETE");
  }
}

export default ApiService;
