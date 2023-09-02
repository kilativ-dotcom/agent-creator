package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        File file = new File((args.length > 0) ? args[0] : "agent.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Config config = objectMapper.readValue(file, Config.class);
            File outputDir = new File(config.getModule() + File.separator);
            outputDir.mkdirs();
            System.out.println("outputDir = " + outputDir.getAbsolutePath());
            System.out.println("config = " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(config));
            writeToFile(new File(outputDir, config.getAgent().getName() + ".hpp"), config.getAgentHPP());
            writeToFile(new File(outputDir, config.getAgent().getName() + ".cpp"), config.getAgentCPP());
            writeToFile(new File(outputDir, config.getManager().getName() + ".hpp"), config.getManagerHPP());
            writeToFile(new File(outputDir, config.getManager().getName() + ".cpp"), config.getManagerCPP());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(File file, String content) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}