#!/usr/bin/env bash

echo "Building..."
./gradlew -x test build
echo "Starting up..."
docker-compose up --build