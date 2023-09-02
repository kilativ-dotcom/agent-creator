#!/usr/bin/env bash
set -eo pipefail
java -jar "$(cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd)/../out/artifacts/agent_creator_jar/agent-creator.jar"