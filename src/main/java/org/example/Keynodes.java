package org.example;

public class Keynodes {
    private final String name;

    public Keynodes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static final String baseHPP =
            "#pragma once\n" +
            "\n" +
            "#include \"sc-memory/sc_object.hpp\"\n" +
            "\n" +
            "#include \"%1$s.generated.hpp\"\n" +
            "\n" +
            "namespace %2$s\n" +
            "{\n" +
            "class %1$s : public ScObject\n" +
            "{\n" +
            "  SC_CLASS()\n" +
            "  SC_GENERATED_BODY()\n" +
            "public:\n" +
            "  SC_PROPERTY(Keynode(\"%3$s\"), ForceCreate(ScType::NodeConstClass))\n" +
            "  static ScAddr %3$s;\n" +
            "};\n" +
            "\n" +
            "}  // namespace %2$s\n";
    /*
    1 - Keynodes name
    2 - namespace
    3 - action class
     */

    private static final String baseCPP =
            "#include \"%1$s.hpp\"\n" +
            "\n" +
            "namespace %2$s\n" +
            "{\n" +
            "ScAddr %1$s::%3$s;\n" +
            "}  // namespace %2$s\n";
    /*
    1 - Keynodes name
    2 - namespace
    3 - action class
     */

    public String getHPP(String module, String actionClass) {
        return String.format(baseHPP, name, module, actionClass);
    }

    public String getCPP(String module, String actionClass) {
        return String.format(baseCPP, name, module, actionClass);
    }
}
