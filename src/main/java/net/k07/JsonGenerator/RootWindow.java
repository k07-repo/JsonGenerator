package net.k07.JsonGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RootWindow extends JFrame {

    private static JTextArea input;
    private static JTextField modPrefix;
    private static File outputDirectory;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public RootWindow() {
        this.setLayout(new GridLayout(3, 1));

        input = new JTextArea();
        modPrefix = new JTextField();

        JPanel inputPanel = wrapInScrollPaneAndPanel(input, "Input (separate by new lines)");

        this.add(inputPanel);

        JButton chooseOutputDirButton = new JButton("Choose Output Directory");
        chooseOutputDirButton.addActionListener(e -> {
            outputDirectory = this.chooseOutputDirectory();
        });
        this.add(chooseOutputDirButton);

        JButton createJsonsButton = new JButton("Start");
        createJsonsButton.addActionListener(e -> {
            String[] inputs = getInputArray();
            for(String input: inputs) {
                generateCubeBlockstate(input, outputDirectory);
            }
        });
        this.add(createJsonsButton);
    }

    private static void generateCubeBlockstate(String registryName, File outputDirectory) {
        String path = "" + ":block/" + registryName;

        Map<String, Object> json = new HashMap<>();
        Map<String, Object> variants = new HashMap<>();
        Map<String, Object> normal = new HashMap<>();
        normal.put("model", path);
        variants.put("", normal);
        json.put("variants", variants);
        File f = new File(outputDirectory, registryName + ".json");

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File chooseOutputDirectory() {
        JFileChooser outputDirChooser = new JFileChooser();
        outputDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = outputDirChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION) {
            File outputDir = outputDirChooser.getSelectedFile();
            return outputDir;
        }
        else {
            return null;
        }


    }
    public String[] getInputArray() {
        String inputText = input.getText();
        String lines[] = inputText.split("\\r?\\n");
        return lines;
    }

    public JPanel wrapInScrollPaneAndPanel(Component c, String name) {
        JScrollPane pane = new JScrollPane(c);
        JPanel panel = new JPanel(new GridLayout());
        panel.setBorder(BorderFactory.createTitledBorder(name));
        panel.add(pane);
        return panel;
    }


}
