package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.stream.Collectors;

public class Agent {
    private String actionClass;
    private String keynodes;
    private String name;
    private List<Parameter> parameters;
    private Config config;

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public String getKeynodes() {
        return keynodes;
    }

    public void setKeynodes(String keynodes) {
        this.keynodes = keynodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static final String baseHPP =
        "#pragma once\n" +
        "\n" +
        "#include \"sc-memory/kpm/sc_agent.hpp\"\n" +
        "\n" +
        "#include \"sc-agents-common/keynodes/coreKeynodes.hpp\"\n" +
        "\n" +
        "#include \"%1$s.generated.hpp\"\n" +
        "\n" +
        "namespace %2$s\n" +
        "{\n" +
        "class %1$s : public ScAgent\n" +
        "{\n" +
        "public:\n" +
        "  SC_CLASS(Agent, Event(scAgentsCommon::CoreKeynodes::question_initiated, ScEvent::Type::AddOutputEdge))\n" +
        "  SC_GENERATED_BODY()\n" +
        "\n" +
        "private:\n" +
        "  bool checkActionClass(ScAddr const & actionNode);\n" +
        "};\n" +
        "\n" +
        "}  // namespace %2$s\n";

    @JsonIgnore
    public String getHPP() {
        return String.format(baseHPP, name, config.getModule());
    }

    private static final String baseCPP =
            "#include \"sc-agents-common/utils/AgentUtils.hpp\"\n" +
            "#include \"sc-agents-common/utils/IteratorUtils.hpp\"\n" +
            "\n" +
            "#include \"keynodes/%1$s.hpp\"\n" +
            "\n" +
            "#include \"manager/%2$s.hpp\"\n" +
            "\n" +
            "#include \"%3$s.hpp\"\n" +
            "\n" +
            "namespace %4$s\n" +
            "{\n" +
            "SC_AGENT_IMPLEMENTATION(%3$s)\n" +
            "{\n" +
            "  ScAddr const & actionNode = otherAddr;\n" +
            "  try\n" +
            "  {\n" +
            "    if (checkActionClass(actionNode) == SC_FALSE)\n" +
            "      return SC_RESULT_OK;\n" +
            "    SC_LOG_INFO(\"%3$s started\");\n" +
            "\n"+
            "%6$s\n" +
            "    if (answerElements.empty())\n" +
            "      SC_THROW_EXCEPTION(utils::ScException, \"%3$s: answer is empty\");\n" +
            "\n" +
            "    utils::AgentUtils::finishAgentWork(&m_memoryCtx, actionNode, answerElements, true);\n" +
            "    SC_LOG_INFO(\"%3$s finished\");\n" +
            "    return SC_RESULT_OK;\n" +
            "  }\n" +
            "  catch (utils::ScException const & exception)\n" +
            "  {\n" +
            "    SC_LOG_ERROR(exception.Description());\n" +
            "    utils::AgentUtils::finishAgentWork(&m_memoryCtx, actionNode, false);\n" +
            "    SC_LOG_INFO(\"%3$s finished with error\");\n" +
            "    return SC_RESULT_ERROR;\n" +
            "  }\n" +
            "}\n" +
            "\n" +
            "bool %3$s::checkActionClass(ScAddr const & actionNode)\n" +
            "{\n" +
            "  return m_memoryCtx.HelperCheckEdge(\n" +
            "      %1$s::%5$s, actionNode, ScType::EdgeAccessConstPosPerm);\n" +
            "}\n" +
            "\n" +
            "}  // namespace %4$s\n";

    private static final String baseParameter =
            "\n" +
            "    ScAddr const & %1$s =\n" +
            "        utils::IteratorUtils::getAnyByOutRelation(&m_memoryCtx, actionNode, %2$s);\n" +
            "    if (%1$s.IsValid() == SC_FALSE)\n" +
            "      SC_THROW_EXCEPTION(utils::ExceptionInvalidParams, \"%3$s: %1$s is not valid\");\n";

    private static final String baseManagerCall =
            "\n" +
            "    auto const & manager = std::make_unique<%1$s>(&m_memoryCtx);\n" +
            "    ScAddrVector const & answerElements = manager->manage({%2$s});\n";

    private String createParametersPart() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Parameter parameter : parameters) {
            stringBuilder.append(String.format(baseParameter, parameter.getName(), parameter.getRelation(), name));
        }
        stringBuilder.append(String.format(baseManagerCall, config.getManager().getName(), parameters.stream().map(Parameter::getName).collect(Collectors.joining(", "))));
        return stringBuilder.toString();
    }

    @JsonIgnore
    public String getCPP() {
        return String.format(baseCPP, keynodes, config.getManager().getName(), name, config.getModule(), actionClass, createParametersPart());
    }
}
