package net.k07.jsongenerator;

import javafx.scene.paint.Material;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class BlockPropertiesBuilderWindow extends JFrame {
    public final String[] MATERIAL_CHOICES = {"ROCK", "WOOD", "EARTH", "ORGANIC", "LEAVES", "PLANTS", "GLASS", "AIR", "WATER", "LAVA", "SNOW", "FIRE", "MISCELLANEOUS", "WEB", "CLAY", "SAND", "SPONGE", "ANVIL", "GOURD", "CAKE"};
    public final String[] SOUND_CHOICES = {"", "WOOD", "GROUND", "PLANT", "STONE", "METAL", "GLASS", "CLOTH", "SAND", "SNOW", "LADDER", "ANVIL", "SLIME", "WET_GRASS", "CORAL", "BAMBOO", "BAMBOO_SAPLING", "SCAFFOLDING", "SWEET_BERRY_BUSH", "CROP", "STEM", "NETHER_WART", "LANTERN"};
    public final String[] HARVEST_CHOICES = {"", "IRON", "DIAMOND"};
    public final String[] HARVEST_TOOL_CHOICES = {"", "PICKAXE", "SHOVEL"};

    public JComboBox materials = new JComboBox(MATERIAL_CHOICES);
    public JComboBox sounds = new JComboBox(SOUND_CHOICES);
    public JComboBox harvest = new JComboBox(HARVEST_CHOICES);
    public JComboBox harvestTools = new JComboBox(HARVEST_TOOL_CHOICES);
    public JTextField hardness = new JTextField();
    public JTextField resistance = new JTextField();
    public JTextField baseClass = new JTextField();
    public JTextField prefix = new JTextField();
    public JTextArea input = new JTextArea();
    public JTextArea output = new JTextArea();

    public BlockPropertiesBuilderWindow() {
        this.setLayout(new GridLayout(7, 1));
        this.setSize(500, 500);

        JPanel p1 = componentWithLabel(materials, "Material");
        this.add(p1);
        JPanel p2 = componentWithLabel(sounds, "Sounds");
        this.add(p2);
        JPanel p3 = new JPanel(new GridLayout(1, 2));
        JPanel p3a = componentWithLabel(hardness, "Hardness");
        JPanel p3b = componentWithLabel(resistance, "Resistance");
        p3.add(p3a);
        p3.add(p3b);
        this.add(p3);
        JPanel p4 = new JPanel(new GridLayout(1, 2));
        JPanel p4a = componentWithLabel(harvest, "Harvest Level");
        JPanel p4b = componentWithLabel(harvestTools, "Harvest Tool");
        p4.add(p4a);
        p4.add(p4b);
        this.add(p4);
        JPanel p5 = new JPanel(new GridLayout(1, 2));
        JPanel p5a = componentWithLabel(baseClass, "Base Class");
        JPanel p5b = componentWithLabel(prefix, "Prefix");
        p5.add(p5a);
        p5.add(p5b);
        this.add(p5);
        JPanel p6 = new JPanel(new GridLayout(1, 2));
        JPanel p6a = wrapInScrollPaneAndPanel(input, "Input");
        JPanel p6b = wrapInScrollPaneAndPanel(output, "Output");
        p6.add(p6a);
        p6.add(p6b);
        this.add(p6);

        JButton start = new JButton();
        start.addActionListener(e -> {
            String result = "";

            if(validateInputs()) {
                BlockPropertiesStringBuilder builder = createBuilder();
                String[] inputArray = getInputArray();


                for(String s: inputArray) {
                    result += builder.build(s) + "\n";
                }
            }

            output.setText(result);
        });
        this.add(start);
    }

    public boolean validateInputs() {
        try {
            Float.parseFloat(hardness.getText());
        }
        catch(NumberFormatException e) {
            MessageUtils.showErrorMessage("Hardness must be numeric!");
            return false;
        }

        try {
            Float.parseFloat(resistance.getText());
        }
        catch(NumberFormatException e) {
            MessageUtils.showErrorMessage("Resistance must be numeric!");
            return false;
        }

        if(baseClass.getText() == "") {
            MessageUtils.showErrorMessage("Base class cannot be empty!");
            return false;
        }

        if(prefix.getText() == "") {
            MessageUtils.showErrorMessage("Prefix cannot be empty!");
            return false;
        }

        return true;
    }


    public BlockPropertiesStringBuilder createBuilder() {
        BlockPropertiesStringBuilder builder = new BlockPropertiesStringBuilder();

        builder.setMaterial(materials.getSelectedItem().toString());

        String selectedSound = sounds.getSelectedItem().toString();
        if(selectedSound != "") {
            builder.setSoundType(selectedSound);
        }

        builder.setHardness(Float.parseFloat(hardness.getText()));
        builder.setResistance(Float.parseFloat(resistance.getText()));

        String selectedHarvestLevel = harvest.getSelectedItem().toString();
        if(selectedHarvestLevel != "") {
            builder.setHarvestTool(selectedHarvestLevel);
        }
        String selectedHarvestTool = harvestTools.getSelectedItem().toString();
        if(selectedHarvestTool != "") {
            builder.setHarvestTool(selectedHarvestTool);
        }

        builder.setBaseClass(baseClass.getText());
        builder.setResourcePrefix(prefix.getText());

        return builder;
    }

    public JPanel componentWithLabel(Component c, String name) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel(name));
        panel.add(c);
        return panel;
    }

    public JPanel wrapInScrollPaneAndPanel(Component c, String name) {
        JScrollPane pane = new JScrollPane(c);
        JPanel panel = new JPanel(new GridLayout());
        panel.setBorder(BorderFactory.createTitledBorder(name));
        panel.add(pane);
        return panel;
    }

    public String[] getInputArray() {
        String inputText = input.getText();
        String lines[] = inputText.split("\\r?\\n");
        return lines;
    }
}
