#!/usr/bin/env bash
set -eo pipefail
ROOT_PATH="$(cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd)/.."
java -jar "$ROOT_PATH/out/artifacts/agent_creator_jar/agent-creator.jar" "$ROOT_PATH/agent.json"
