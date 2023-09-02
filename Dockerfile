FROM openjdk:8-jre

WORKDIR .

COPY out/artifacts/agent_creator_jar/agent-creator.jar /app/app.jar
COPY agent.json /app/agent.json
COPY scripts/create-agent /usr/bin/
