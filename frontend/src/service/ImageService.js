import ApiService from "@/service/ApiService";

class ImageService {
  getCharacterPortrait(characterId) {
    return `${process.env.NEXT_PUBLIC_API_URL}/characters/${characterId}/image`;
  }

  async uploadCharacterImage(characterId, formData) {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/characters/${characterId}/upload`,
      {
        method: "POST",
        body: formData,
      },
    );

    if (!response.ok) {
      throw new Error(`Failed to upload image: ${response.statusText}`);
    }
  }
}

export default new ImageService();
