#FROM node:alpine AS builder
#
#WORKDIR /usr/app
#
#COPY . .
#
#RUN npm install && npm run build

FROM nginx:alpine

#COPY --from=builder /app/dist/* /usr/share/nginx/html/
COPY ./dist/* /usr/share/nginx/html/
