package net.k07.jsongenerator;

public class BlockPropertiesStringBuilder {

    private String material;
    private String soundType;
    private float hardness;
    private float resistance;
    private int lightValue;
    private String harvestLevel;
    private String harvestTool;
    private String resourcePrefix;
    private String baseClass;

    private boolean sameResistanceAsHardness;

    public String build(String registryName) {
        String result = "registry.register(new " + baseClass + "(Block.Properties.create(Material." + material + ")";
        if(soundType != null) {
            result += ".sound(SoundType." + soundType + ")";
        }

        if(hardness == 0 && resistance == 0) {
            result += ".zeroHardnessAndResistance()";
        }
        else if(sameResistanceAsHardness) {
            result += ".hardnessAndResistance(" + hardness + ")";
        }
        else {
            result += ".hardnessAndResistance(" + hardness + "F, " + resistance + "F)";
        }

        if(harvestLevel != null) {
            result += ".harvestLevel(" + harvestLevel + ").harvestTool(ToolType." + harvestTool + ")";
        }

        if(lightValue > 0 && lightValue <= 15) {
            result += ".lightValue(" + lightValue + ")";
        }

        result += ").setRegistryName(" + resourcePrefix + ", \"" + registryName + "\"));";

        return result;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setSoundType(String soundType) {
        this.soundType = soundType;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    public void setHarvestLevel(String harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public void setHarvestTool(String harvestTool) {
        this.harvestTool = harvestTool;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }

    public void setResourcePrefix(String prefix) {
        this.resourcePrefix = prefix;
    }
}
