package org.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class Docs {
    private static final String base =
            "It is agent that ...\n" +
            "\n" +
            "**Action class:**\n" +
            "\n" +
            "`%1$s`\n" +
            "\n" +
            "%2$s" +
            "**Workflow:**\n" +
            "\n" +
            "* ...\n" +
            "\n" +
            "### Example\n" +
            "\n" +
            "Example of an input structure:\n" +
            "\n" +
            "<img src=\"../images/%3$sInput.png\"></img>\n" +
            "\n" +
            "Example of an output structure:\n" +
            "\n" +
            "<img src=\"../images/%3$sOutput.png\"></img>\n" +
            "\n" +
            "### Agent implementation language\n" +
            "\n" +
            "C++\n" +
            "\n" +
            "### Result\n" +
            "\n" +
            "Possible result codes:\n" +
            "\n" +
            "* `sc_result_ok`- agent finished successfully;\n" +
            "* `sc_result_error`- internal error.\n";
    /*
    1 - action class
    2 - parameters part
    3 - prefix for images
     */

    private static final String parametersPrefix =
            "**Parameters:**\n" +
            "\n";

    private static final String parameterPart =
            "%1$d. `%2$s` - ...";

    public static final String getMkDocs(String actionClass, List<Parameter> parameters, String imagesPrefix) {
        AtomicInteger parameterIndex = new AtomicInteger(1);
        String parametersPart = parameters.isEmpty() ? "" : parameters.stream()
                .map(parameter -> String.format(parameterPart, parameterIndex.getAndAccumulate(1, Integer::sum), parameter.getName()))
                .collect(Collectors.joining(";\n", parametersPrefix, ".\n\n"));
        return String.format(base, actionClass, parametersPart, imagesPrefix);
    }
}
