package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Config {
    private String module;
    private Agent agent;
    private Manager manager;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
        if (agent != null) {
            agent.setConfig(this);
        }
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
        if (manager != null) {
            manager.setConfig(this);
        }
    }

    @Override
    public String toString() {
        return "Config{" +
                "module='" + module + '\'' +
                ", agent=" + agent +
                ", manager=" + manager +
                '}';
    }

    @JsonIgnore
    String getAgentHPP() {
        if (agent != null) {
            return agent.getHPP();
        } else {
            return "";
        }
    }
    @JsonIgnore
    String getAgentCPP() {
        if (agent != null) {
            return agent.getCPP();
        } else {
            return "";
        }
    }

    @JsonIgnore
    String getManagerHPP() {
        if (manager != null) {
            return manager.getHPP();
        } else {
            return "";
        }
    }

    @JsonIgnore
    String getManagerCPP() {
        if (manager != null) {
            return manager.getCPP();
        } else {
            return "";
        }
    }
}
