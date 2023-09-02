this is a script that creates .cpp and .hpp files for your agent and manager

## CLONE
to clone this project go to desired directory on your computer and run in terminal run
```bash
git clone git@github.com:kilativ-dotcom/agent-creator.git
```

## NATIVELY
before running this script change `agent.json` to meet your requirements for agent configuration and then run
```bash
./scripts/start.sh
```
files will be created in a directory with name matching module name from `agent.json`

## DOCKER
to run script using docker you'll need execute commands in two terminals, both terminals have to be in the root of cloned repository
in first terminal run
```bash
docker build -t agent-creator .
./scripts/enter-docker.sh
create-agent
```
then in second terminal run command (replace setTheoryModule with module name you used in `agent.json`)
```bash
docker cp agent-creator:setTheoryModule ./
```
after executing command in second terminal you will have a copy of this module on your computer and you can type `exit` in first terminal or press `CTRL + D` to exit docker mode
