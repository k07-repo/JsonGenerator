package net.k07.JsonGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static File outputDir;
    public static final String prefix = "";
    public static void main(String[] args) {
        JFileChooser outputDirChooser = new JFileChooser();
        outputDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        outputDirChooser.showOpenDialog(null);

        outputDir = outputDirChooser.getSelectedFile();
    }

    private static void generateCubeBlockstate(String registryName) {
        String path = prefix + ":block/" + registryName;

        Map<String, Object> json = new HashMap<>();
        Map<String, Object> variants = new HashMap<>();
        Map<String, Object> normal = new HashMap<>();
        normal.put("model", path);
        variants.put("", normal);
        json.put("variants", variants);
        File f = new File(outputDir, registryName + ".json");

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
