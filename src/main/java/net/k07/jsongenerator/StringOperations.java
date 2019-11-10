package net.k07.jsongenerator;

public class StringOperations {

    public static String createBlockField(String prefix, String camelCaseName) {
        return createObjectField(prefix, camelCaseName, "Block");
    }

    public static String createItemField(String prefix, String camelCaseName) {
        return createObjectField(prefix, camelCaseName, "Item");
    }

    public static String createObjectField(String prefix, String camelCaseName, String type) {
        return "@ObjectHolder(\"" + createID(prefix, camelCaseToSnakeCase(camelCaseName)) + "\") public static " + type + " " + camelCaseName + ";";
    }

    public static String createBlockItemRegistry(String camelCaseName) {
        return "registry.register(new BlockItem(BlockRegistry." + camelCaseName + ", blockTabProperty).setRegistryName(\"" + camelCaseToSnakeCase(camelCaseName) + "\"))";
    }

    public static String createID(String prefix, String registryName) {
        return prefix + ":" + registryName;
    }

    public static String camelCaseToSnakeCase(String input) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int k = 0; k < input.length(); k++) {
            char current = input.charAt(k);
            if(Character.isLowerCase(current) || current == '.' || current == '_' || Character.isDigit(current)) {
                stringBuilder.append(input.charAt(k));
            }
            else {
                stringBuilder.append("_");
                stringBuilder.append(Character.toLowerCase(current));
            }
        }

        return stringBuilder.toString();
    }

    public static String snakeCaseToCamelCase(String input) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int k = 0; k < input.length(); k++) {
            char current = input.charAt(k);
            if(current != '_') {
                stringBuilder.append(input.charAt(k));
            }
            else {
                k++;
                if(k >= input.length()) {
                    break;
                }
                current = input.charAt(k);
                stringBuilder.append(Character.toUpperCase(current));
            }
        }

        return stringBuilder.toString();
    }
}
