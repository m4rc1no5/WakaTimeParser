#!/usr/bin/env bash

# war
echo -e "Update WakaTimeParser-api war..."
cp -f ../api/target/WakaTimeParser-api.war api/WakaTimeParser-api.war

# up
if [ $# -gt 0 ]; then
    docker-compose up "$@"
else
    docker-compose up
fi