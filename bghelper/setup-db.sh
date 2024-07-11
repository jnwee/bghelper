#!/bin/bash

docker compose run --name mongosetup -d mongo
docker exec -it mongosetup /bin/bash -c "mongorestore"
docker container stop mongosetup
