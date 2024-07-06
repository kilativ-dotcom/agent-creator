package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        File file = new File((args.length > 0) ? args[0] : "agent.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Config config = objectMapper.readValue(file, Config.class);
            File outputDir = new File(config.getModule().getNamespace() + File.separator);
            outputDir.mkdirs();
            System.out.println("outputDir = " + outputDir.getAbsolutePath());
            System.out.println("config = " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(config));
            writeToFile(new File(outputDir, config.getModule().getName() + ".hpp"), config.getModuleHPP());
            writeToFile(new File(outputDir, config.getModule().getName() + ".cpp"), config.getModuleCPP());
            File agentDir = new File(outputDir, "agent");
            agentDir.mkdirs();
            writeToFile(new File(agentDir, config.getAgent().getName() + ".hpp"), config.getAgentHPP());
            writeToFile(new File(agentDir, config.getAgent().getName() + ".cpp"), config.getAgentCPP());
            if (config.getKeynodes() != null) {
                File keynodesDir = new File(outputDir, "keynodes");
                keynodesDir.mkdirs();
                writeToFile(new File(keynodesDir, config.getKeynodes().getName() + ".hpp"), config.getKeynodesHPP());
                writeToFile(new File(keynodesDir, config.getKeynodes().getName() + ".cpp"), config.getKeynodesCPP());
            }
            if (config.getManager() != null) {
                File managerDir = new File(outputDir, "manager");
                managerDir.mkdirs();
                writeToFile(new File(managerDir, config.getManager().getName() + ".hpp"), config.getManagerHPP());
                writeToFile(new File(managerDir, config.getManager().getName() + ".cpp"), config.getManagerCPP());
            }
            if (config.isCreateTest())
            {
                File testDir = new File(outputDir, "test" + File.separator + "units" + File.separator);
                testDir.mkdirs();
                writeToFile(new File(testDir, "test" + config.getAgent().getName() + ".cpp"), Test.getCPP(config.getAgent().getName(), config.getKeynodes().getName(), config.getModule().getNamespace()));
            }
            if (config.isCreateKB()) {
                File kbDir = new File(outputDir, "kb");
                kbDir.mkdirs();
                writeToFile(new File(kbDir, config.getAgent().getActionClass() + ".scs"), KB.getSCS(config.getAgent().getActionClass()));
            }
            if (config.isCreateDocs()) {
                File docsDir = new File(outputDir, "docs");
                docsDir.mkdirs();
                String agentNameFirstLetterLowercase = config.getAgent().getName().substring(0, 1).toLowerCase() + config.getAgent().getName().substring(1);
                writeToFile(new File(docsDir, agentNameFirstLetterLowercase + ".md"), Docs.getMkDocs(config.getAgent().getActionClass(), config.getAgent().getParameters(), agentNameFirstLetterLowercase));
            }
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