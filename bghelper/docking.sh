#!/bin/bash

if [ -f .needs-setup ]; then
    echo "First time launching application, setting up database..."
    chmod +x setup-db.sh
    ./setup-db.sh
    rm -f .needs-setup
else
    echo "Database already set up, continuing..."
fi

docker compose up
