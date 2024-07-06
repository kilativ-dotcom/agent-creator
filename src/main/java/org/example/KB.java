package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KB {
    private static final String base =
            "%1$s\n" +
            "<- sc_node_class;\n" +
            "=> nrel_main_idtf:\n" +
            "    [...](* <- lang_ru;; *);\n" +
            "    [%2$s](* <- lang_en;; *);\n" +
            "<- actions_class;\n" +
            "<- atomic_action_class;\n" +
            "<= nrel_inclusion: information_action;\n" +
            "<- rrel_key_sc_element:\n" +
            "    ...\n" +
            "    (*\n" +
            "    <- explanation;;\n" +
            "    <= nrel_sc_text_translation:\n" +
            "        {\n" +
            "        rrel_example: [...]\n" +
            "            (* <- lang_ru;;*);\n" +
            "        rrel_example: [%3$s is an action of ...]\n" +
            "            (* <- lang_en;; *)\n" +
            "        };;\n" +
            "    *);;\n";
    /*
    1 - action class
    2 - en idtf of action class
    3 - en idtf of action class with first letter capitalized
     */

    public static final String getSCS(String actionClass) {
        String[] parts = actionClass.split("_");
        if (parts.length == 0) {
            return "";
        }
        String firstPart = parts[0].toLowerCase();
        String firstPartWithUppercaseStart = firstPart.substring(0, 1).toUpperCase() + firstPart.substring(1);
        String joinedEnding = Arrays.stream(parts).skip(1).collect(Collectors.joining(" ", ". ", ""));
        return String.format(base, actionClass, firstPart + joinedEnding, firstPartWithUppercaseStart + joinedEnding);
    }
}
