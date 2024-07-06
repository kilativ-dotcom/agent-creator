package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Module {
    private String namespace;
    private String name;
    private ActionDeactivationChecker actionDeactivationChecker;

    public void setName(String name) {
        namespace = name.substring(0, 1).toLowerCase() + name.substring(1);
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setActionDeactivationChecker(ActionDeactivationChecker actionDeactivationChecker) {
        this.actionDeactivationChecker = actionDeactivationChecker;
    }

    public class ActionDeactivationChecker {
        private String file;
        private String name;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static final String baseHPP =
            "#pragma once\n" +
            "\n" +
            "#include \"sc-memory/sc_module.hpp\"\n" +
            "\n" +
            "#include \"generated/%1$s.generated.hpp\"\n" +
            "\n" +
            "namespace %2$s\n" +
            "{\n" +
            "class %1$s : public ScModule\n" +
            "{\n" +
            "  SC_CLASS(LoadOrder(100))\n" +
            "  SC_GENERATED_BODY()\n" +
            "\n" +
            "  virtual sc_result\n" +
            "\n" +
            "  InitializeImpl() override;\n" +
            "\n" +
            "  virtual sc_result ShutdownImpl() override;\n" +
            "};\n" +
            "\n" +
            "}  // namespace %2$s\n";
    /*
    1 - Module name
    2 - namespace
     */

    @JsonIgnore
    public String getHPP() {
        return String.format(baseHPP, name, namespace);
    }

    private static final String baseCPP =
            "#include \"agent/%1$s.hpp\"\n" +
            "\n" +
            "#include \"keynodes/%2$s.hpp\"\n" +
            "\n" +
            "%5$s" +
            "#include \"%3$s.hpp\"\n" +
            "\n" +
            "namespace %4$s\n" +
            "{\n" +
            "SC_IMPLEMENT_MODULE(%3$s)\n" +
            "\n" +
            "sc_result %3$s::InitializeImpl()\n" +
            "{\n" +
            "%6$s" +
            "\n" +
            "  return SC_RESULT_OK;\n" +
            "}\n" +
            "\n" +
            "sc_result %3$s::ShutdownImpl()\n" +
            "{\n" +
            "  SC_AGENT_UNREGISTER(%1$s)\n" +
            "\n" +
            "  return SC_RESULT_OK;\n" +
            "}\n" +
            "}  // namespace %4$s\n";
    /*
    1 - Agent name
    2 - Keynodes name
    3 - Module name
    4 - namespace
    5 - deactivation include
    6 - agent registration
     */

    private static final String actionDeactivationInclude =
            "#include \"%1$s\"\n" +
            "\n";
    /*
    1 - filename
     */

    private static final String agentRegistrationWitActionDeactivationCheck =
            "  if (!%1$s::InitGlobal())\n" +
            "  {\n" +
            "    return SC_RESULT_ERROR;\n" +
            "  }\n" +
            "\n" +
            "ScMemoryContext context;\n" +
            "  if (%4$s(&context, %1$s::%2$s))\n" +
            "  {\n" +
            "    SC_LOG_WARNING(\"%2$s is deactivated\");\n" +
            "  }\n" +
            "  else\n" +
            "  {\n" +
            "    SC_AGENT_REGISTER(%3$s)\n" +
            "  }\n";
    /*
    1 - Keynodes name
    2 - agent action class
    3 - Agent name
    4 - deactivation checker
     */

    private static final String defaultAgentRegistration =
            "  SC_AGENT_REGISTER(%1$s)\n";
    /*
    1 - Agent name
     */

    @JsonIgnore
    public String getCPP(String agentName, String keynodesName, String agentActionClass) {
        String deactivationInclude = actionDeactivationChecker == null ? "" : String.format(actionDeactivationInclude, actionDeactivationChecker.getFile());
        String agentRegistration = actionDeactivationChecker == null ? String.format(defaultAgentRegistration, agentName) : String.format(agentRegistrationWitActionDeactivationCheck, keynodesName, agentActionClass, agentName, actionDeactivationChecker.name);
        return String.format(baseCPP, agentName, keynodesName, name, namespace, deactivationInclude, agentRegistration);
    }
}
