#!/usr/bin/env bash
# Run the app with auto-rebuild: starts spring-boot:run (DevTools restarts the
# app whenever target/classes changes) and recompiles when a file under
# src/main is saved. Ctrl+C stops the watcher, Maven and the app together.
set -uo pipefail
cd "$(dirname "$0")"

# Job control puts the app in its own process group so one kill reaches
# the mvnw wrapper, the Maven JVM and the forked app JVM.
set -m
./mvnw -q spring-boot:run &
APP=$!
trap 'kill -TERM -- -"$APP" 2>/dev/null; wait "$APP" 2>/dev/null; exit 0' INT TERM

mkdir -p target
stamp=target/.watch-stamp
touch "$stamp"

while kill -0 "$APP" 2>/dev/null; do
  if [ -n "$(find src/main -type f -newer "$stamp" -print -quit)" ]; then
    touch "$stamp"
    echo ">>> change detected, recompiling"
    ./mvnw -q compile || echo ">>> compile failed, app keeps running the last good build"
  fi
  sleep 1
done
