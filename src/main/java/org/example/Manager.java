package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Manager {
    private Config config;
    private String name;

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static final String baseHPP =
            "#pragma once\n" +
            "\n" +
            "#include \"manager/AgentManager.hpp\"\n" +
            "\n" +
            "namespace %1$s\n" +
            "{\n" +
            "class %2$s : public commonModule::AgentManager\n" +
            "{\n" +
            "public:\n" +
            "  explicit %2$s(ScMemoryContext * context);\n" +
            "\n" +
            "  ScAddrVector manage(ScAddrVector const & processParameters) const override;\n" +
            "};\n" +
            "\n" +
            "}  // namespace %1$s\n";

    @JsonIgnore
    public String getHPP() {
        return String.format(baseHPP, config.getModule(), name);
    }

    private static final String baseCPP =
            "#include \"%1$s.hpp\"\n" +
            "\n" +
            "namespace %2$s\n" +
            "{\n" +
            "%1$s::%1$s(ScMemoryContext * context)\n" +
            "  : AgentManager(context)\n" +
            "{\n" +
            "}\n" +
            "\n" +
            "ScAddrVector %1$s::manage(ScAddrVector const & processParameters) const\n" +
            "{\n" +
            "%3$s" +
            "  return {};\n" +
            "}\n" +
            "}  // namespace %2$s\n";

    private static final String parameterBase =
            "  ScAddr const & %1$s = processParameters[%2$s];\n" +
            "  if (%1$s.IsValid() == SC_FALSE)\n" +
            "    SC_THROW_EXCEPTION(utils::ExceptionInvalidParams, \"%3$s: %1$s is not valid\");\n";

    private String createParametersInitialization() {
        StringBuilder stringBuilder = new StringBuilder();
        int parameterIndex = 0;
        for (Parameter parameter : config.getAgent().getParameters()) {
            stringBuilder.append(String.format(parameterBase, parameter.getName(), parameterIndex++, name));
        }
        return stringBuilder.toString();
    }

    @JsonIgnore
    public String getCPP() {
        return String.format(baseCPP, name, config.getModule(), createParametersInitialization());
    }
}
