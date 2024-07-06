package org.example;

public class Test {
    private static final String baseCPP =
            "#include \"sc-memory/kpm/sc_agent.hpp\"\n" +
            "#include \"sc_test.hpp\"\n" +
            "#include \"scs_loader.hpp\"\n" +
            "\n" +
            "#include \"agent/%1$s.hpp\"\n" +
            "\n" +
            "#include \"keynodes/%2$s.hpp\"\n" +
            "\n" +
            "namespace %3$s\n" +
            "{\n" +
            "ScsLoader loader;\n" +
            "\n" +
            "using %4$s = ScMemoryTest;\n" +
            "\n" +
            "void initialize()\n" +
            "{\n" +
            "  scAgentsCommon::CoreKeynodes::InitGlobal();\n" +
            "  %5$s::%2$s::InitGlobal();\n" +
            "\n" +
            "  ScAgentInit(true);\n" +
            "  SC_AGENT_REGISTER(%5$s::%1$s)\n" +
            "}\n" +
            "\n" +
            "void shutdown()\n" +
            "{\n" +
            "  SC_AGENT_UNREGISTER(%5$s::%1$s)\n" +
            "}\n" +
            "\n" +
            "TEST_F(%4$s, successfulAction)\n" +
            "{\n" +
            "  initialize();\n" +
            "  shutdown();\n" +
            "}\n" +
            "}  // namespace %3$s\n";
    /*
    1 - Agent name
    2 - Keynodes name
    3 - namespace for test
    4 - class name for test
    5 - module name
     */
    public static String getCPP(String agentName, String keynodesName, String moduleName) {
        return String.format(baseCPP, agentName, keynodesName, "test" + agentName,  "Test" + agentName, moduleName);
    }
}
