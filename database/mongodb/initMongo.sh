#!/bin/bash

mongoimport --db aperitech --collection weathers --type json --file /weathers.json --jsonArray --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin

mongoimport --db aperitech --collection datas --type json --file /datas.json --jsonArray --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin
