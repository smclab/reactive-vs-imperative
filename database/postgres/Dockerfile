FROM postgres:10-alpine

#ENV POSTGRES_PASSWORD postgres

COPY schema.sql /docker-entrypoint-initdb.d/1-schema.sql
COPY data.sql /docker-entrypoint-initdb.d/2-data.sql
