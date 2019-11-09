package net.k07.jsongenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RootWindow extends JFrame {

    enum ModelType {
        CUBE, CROSS
    }
    private static JTextArea input;
    private static JTextArea output;
    private static JTextField modPrefix;
    private static File outputDirectory;
    private static JTextField outputDirTextField;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public RootWindow() {
        this.setTitle("JSON Generator");
        this.setLayout(new GridLayout(3, 1));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        input = new JTextArea();
        output = new JTextArea();
        modPrefix = new JTextField();

        JPanel topPanel = new JPanel(new GridLayout(3, 1));

        JPanel prefixPanel = componentWithLabel(modPrefix, "Prefix");
        topPanel.add(prefixPanel);

        String[] choices = {"Cube Blockstate", "Drop-Self Loot Table", "Cube Model", "Cross Model", "Block Item Model", "Special Item Model", "Create Block Object Fields", "Create Block Item Registry", "Camel Case to Snake Case", "Snake Case to Camel Case", "Properties Builder", "Bulk File Rename (Camel Case to Snake Case)"};
        JComboBox jsonList = new JComboBox(choices);
        jsonList.setSelectedIndex(0);
        JPanel choicesPanel = componentWithLabel(jsonList, "Action");
        topPanel.add(choicesPanel);

        JPanel outputOptionsPanel = new JPanel(new GridLayout(1, 2));
        JButton chooseOutputDirButton = new JButton("Choose Output Directory");
        chooseOutputDirButton.addActionListener(e -> {
            outputDirectory = this.chooseOutputDirectory();
            outputDirTextField.setText(outputDirectory.toString());
        });
        outputDirTextField = new JTextField();
        outputDirTextField.setEditable(false);
        outputOptionsPanel.add(chooseOutputDirButton);
        outputOptionsPanel.add(outputDirTextField);
        topPanel.add(outputOptionsPanel);

        this.add(topPanel);

        JPanel inOutPanel = new JPanel(new GridLayout(1, 2));

        JPanel inputPanel = wrapInScrollPaneAndPanel(input, "Input (separate by new lines)");
        inOutPanel.add(inputPanel);

        JPanel outputPanel = wrapInScrollPaneAndPanel(output, "Output");
        inOutPanel.add(outputPanel);

        this.add(inOutPanel);

        JButton createJsonsButton = new JButton("Start");
        createJsonsButton.addActionListener(e -> {
            switch(jsonList.getSelectedIndex()) {
                case 0:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an output directory first!");
                        return;
                    }
                    bulkGenerateCubeBlockstates(getInputArray());
                    return;

                case 1:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an output directory first!");
                        return;
                    }
                    bulkGenerateSelfLootTables(getInputArray());
                    return;

                case 2:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an output directory first!");
                        return;
                    }
                    bulkGenerateCubeModels(getInputArray());
                    return;

                case 3:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an output directory first!");
                        return;
                    }
                    bulkGenerateCrossModels(getInputArray());
                    return;


                case 4:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an output directory first!");
                        return;
                    }
                    bulkGenerateItemBlockModels(getInputArray());
                    return;

                case 5:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an output directory first!");
                        return;
                    }
                    bulkGenerateSpecialItemModels(getInputArray());
                    return;

                case 6:
                    bulkGenerateBlockObjectFields(getInputArray());
                    return;

                case 7:
                    bulkCreateBlockItemRegistries(getInputArray());
                    return;

                case 8:
                    bulkCamelCaseToSnakeCase(getInputArray());
                    return;

                case 9:
                    bulkSnakeCaseToCamelCase(getInputArray());
                    return;

                case 10:
                    BlockPropertiesBuilderWindow builderWindow = new BlockPropertiesBuilderWindow();
                    builderWindow.setVisible(true);
                    return;

                case 11:
                    if(outputDirectory == null) {
                        MessageUtils.showErrorMessage("Select an input directory first!");
                        return;
                    }
                    FileOperations.renameAllFirstLevelFiles(outputDirectory);
                    return;
            }

            MessageUtils.showSuccessMessage("Success!");
        });
        this.add(createJsonsButton);
    }

    private static void bulkGenerateCubeBlockstates(String[] inputs) {
        for(String s: inputs) {
            generateCubeBlockstate(s, modPrefix.getText(), outputDirectory);
        }
    }

    private static void bulkGenerateSelfLootTables(String[] inputs) {
        for(String s: inputs) {
            generateSelfLootTable(s, modPrefix.getText(), outputDirectory);
        }
    }

    private static void bulkGenerateCubeModels(String[] inputs) {
        for(String s: inputs) {
            generateBasicModel(s, ModelType.CUBE, modPrefix.getText(), outputDirectory);
        }
    }

    private static void bulkGenerateCrossModels(String[] inputs) {
        for(String s: inputs) {
            generateBasicModel(s, ModelType.CROSS, modPrefix.getText(), outputDirectory);
        }
    }

    private static void bulkGenerateItemBlockModels(String[] inputs) {
        for(String s: inputs) {
            generateItemBlockModel(s, modPrefix.getText(), outputDirectory);
        }
    }

    private static void bulkGenerateSpecialItemModels(String[] inputs) {
        for(String s: inputs) {
            generateSpecialItemModel(s, modPrefix.getText(), outputDirectory);
        }
    }

    private static void bulkGenerateBlockObjectFields(String[] inputs) {
        output.setText("");
        for(String s: inputs) {
            output.append(StringOperations.createBlockField(modPrefix.getText(), s) + "\n");
        }
    }

    private static void bulkCreateBlockItemRegistries(String[] inputs) {
        output.setText("");
        for(String s: inputs) {
            output.append(StringOperations.createBlockItemRegistry(s) + "\n");
        }
    }

    private static void bulkSnakeCaseToCamelCase(String[] inputs) {
        output.setText("");
        for(String s: inputs) {
            output.append(StringOperations.snakeCaseToCamelCase(s) + "\n");
        }
    }

    private static void bulkCamelCaseToSnakeCase(String[] inputs) {
        output.setText("");
        for(String s: inputs) {
            output.append(StringOperations.camelCaseToSnakeCase(s) + "\n");
        }
    }


    private static void generateCubeBlockstate(String registryName, String prefix, File outputDirectory){
        String path = prefix + ":" + registryName;

        JsonObject model = new JsonObject();
        model.addProperty("model", path);

        JsonObject blankVariant = new JsonObject();
        blankVariant.add("normal", model);

        JsonObject variants = new JsonObject();
        variants.add("variants", blankVariant);

        File f = new File(outputDirectory, registryName + ".json");

        try(FileWriter w = new FileWriter(f)) {
            GSON.toJson(variants, w);
        }
        catch(IOException e) {
            MessageUtils.showErrorMessage(e.getStackTrace().toString());
        }
    }

    private static void generateBasicModel(String registryName, ModelType type, String prefix, File outputDirectory){
        String path = prefix + ":blocks/" + registryName;
        String textureType, modelType;
        switch(type) {
            case CUBE:
                textureType = "all";
                modelType = "block/cube_all";
                break;

            case CROSS:
                textureType = "cross";
                modelType = "block/cross";
                break;

            default:
                MessageUtils.showErrorMessage("No type selected. This should not occur, please report this bug.");
                textureType = "all";
                modelType = "block/cube_all";
                break;
        }

        JsonObject all = new JsonObject();
        all.addProperty(textureType, path);

        JsonObject top = new JsonObject();
        top.addProperty("parent", modelType);
        top.add("textures", all);

        File f = new File(outputDirectory, registryName + ".json");

        try(FileWriter w = new FileWriter(f)) {
            GSON.toJson(top, w);
        }
        catch(IOException e) {
            MessageUtils.showErrorMessage(e.getStackTrace().toString());
        }
    }

    private static void generateItemBlockModel(String registryName, String prefix, File outputDirectory) {
        String path = prefix + ":block/" + registryName;
        JsonObject parent = new JsonObject();
        parent.addProperty("parent", path);

        File f = new File(outputDirectory, registryName + ".json");

        try(FileWriter w = new FileWriter(f)) {
            GSON.toJson(parent, w);
        }
        catch(IOException e) {
            MessageUtils.showErrorMessage(e.getStackTrace().toString());
        }
    }

    private static void generateSpecialItemModel(String registryName, String prefix, File outputDirectory) {
        String path = prefix + ":items/" + registryName;

        JsonObject top = new JsonObject();
        top.addProperty("parent", "item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", path);

        top.add("textures", textures);

        File f = new File(outputDirectory, registryName + ".json");

        try(FileWriter w = new FileWriter(f)) {
            GSON.toJson(top, w);
        }
        catch(IOException e) {
            MessageUtils.showErrorMessage(e.getStackTrace().toString());
        }
    }

    private static void generateSelfLootTable(String registryName, String prefix, File outputDirectory) {
        String path = prefix + ":block/" + registryName;

        JsonObject top = new JsonObject();
        top.addProperty("type", "minecraft:block");

        JsonArray pools = new JsonArray();
        JsonObject pool = new JsonObject();
        pool.addProperty("name", "self");
        pool.addProperty("rolls", 1);

        JsonArray entries = new JsonArray();
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", prefix + ":" + registryName);
        entries.add(entry);
        pool.add("entries", entries);

        JsonArray conditions = new JsonArray();
        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:survives_explosion");
        conditions.add(condition);
        pool.add("conditions", conditions);
        pools.add(pool);
        top.add("pools", pools);

        File f = new File(outputDirectory, registryName + ".json");
        try(FileWriter w = new FileWriter(f)) {
            GSON.toJson(top, w);
        }
        catch(IOException e) {
            MessageUtils.showErrorMessage(e.getStackTrace().toString());
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

    public JPanel componentWithLabel(Component c, String name) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel(name));
        panel.add(c);
        return panel;
    }



}
