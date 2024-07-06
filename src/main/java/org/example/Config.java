package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Config {
    private Module module;
    private Agent agent;
    private Manager manager;
    private Keynodes keynodes;
    private boolean createTest;
    private boolean createDocs;
    private boolean createKB;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
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

    @JsonIgnore
    public Keynodes getKeynodes() {
        return keynodes;
    }

    public void setKeynodes(Keynodes keynodes) {
        this.keynodes = keynodes;
    }

    public boolean isCreateTest() {
        return createTest;
    }

    public void setCreateTest(boolean createTest) {
        this.createTest = createTest;
    }

    public boolean isCreateDocs() {
        return createDocs;
    }

    public void setCreateDocs(boolean createDocs) {
        this.createDocs = createDocs;
    }

    public boolean isCreateKB() {
        return createKB;
    }

    public void setCreateKB(boolean createKB) {
        this.createKB = createKB;
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
    String getModuleHPP() {
        if (module != null && agent != null && keynodes != null) {
            return module.getHPP();
        } else {
            return "";
        }
    }
    @JsonIgnore
    String getModuleCPP() {
        if (module != null && agent != null && keynodes != null) {
            return module.getCPP(agent.getName(), keynodes.getName(), agent.getActionClass());
        } else {
            return "";
        }
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

    @JsonIgnore
    String getKeynodesHPP() {
        if (keynodes != null) {
            return keynodes.getHPP(module.getNamespace(), agent.getActionClass());
        } else {
            return "";
        }
    }

    @JsonIgnore
    String getKeynodesCPP() {
        if (keynodes != null) {
            return keynodes.getCPP(module.getNamespace(), agent.getActionClass());
        } else {
            return "";
        }
    }
}
