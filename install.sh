#!/usr/bin/env bash

set -e
set -o pipefail

case "$1" in
debug) MODE="debugExecutable" ;;
release) MODE="releaseExecutable" ;;
*) echo "Specify build type: debug or release" && exit 1 ;;
esac

case "$OSTYPE" in
linux*) OS="linux" ;;
darwin*) OS="macos" ;;
*) echo "Unsupported operating system: $OSTYPE" && exit 1 ;;
esac

case "$(arch)" in
x86_64) ARCH="X64" ;;
arm64) ARCH="Arm64" ;;
*) echo "Unsupported architecture: $(arch)" && exit 1 ;;
esac

sudo ln -sf "$PWD/build/bin/$OS$ARCH/$MODE/todo.kexe" "/usr/local/bin/todo"
