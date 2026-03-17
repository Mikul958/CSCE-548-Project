# ---------- Stage 1: Build Angular ----------
FROM node:22-alpine AS build
WORKDIR /app

# Install dependencies first (better caching)
COPY package*.json ./
RUN npm install

# Copy the rest of the code and build
COPY . .
RUN npm run build --configuration production

# ---------- Stage 2: Serve with Nginx ----------
FROM nginx:alpine
# Copy the build output to Nginx's default public folder
COPY --from=build /app/dist/speedrun-fe/browser /usr/share/nginx/html

# Copy a custom Nginx config to handle Angular routing
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]