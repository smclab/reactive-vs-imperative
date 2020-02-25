FROM mongo:latest

#ENV MONGO_INITDB_ROOT_USERNAME mongoadmin
#ENV MONGO_INITDB_ROOT_PASSWORD secret

COPY mongoBackup.tgz mongoBackup.tgz

RUN tar -zxvf mongoBackup.tgz

COPY initMongo.sh /docker-entrypoint-initdb.d/initMongo.sh
