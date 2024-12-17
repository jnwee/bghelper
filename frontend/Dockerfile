# Base image for development
FROM node:18

# Set working directory
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Expose port 3000 for Next.js
EXPOSE 3000

# Start the Next.js dev server
CMD ["npm", "run", "dev"]
