FROM nginx:1.27-alpine
LABEL maintainer="Antonio Scardace"

EXPOSE 8000

COPY static /usr/share/static
COPY certs /etc/nginx/certs
COPY nginx.conf /etc/nginx/nginx.conf