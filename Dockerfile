FROM postgres:14.2-alpine

ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_DB=postgres

COPY *.sql /docker-entrypoint-initdb.d/

EXPOSE 5432