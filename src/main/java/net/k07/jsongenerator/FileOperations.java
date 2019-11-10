package net.k07.jsongenerator;

import java.io.File;

public class FileOperations {

    public static boolean renameFile(File parentDirectory, String old, String renameTo) {
        File file1 = new File(parentDirectory.getAbsolutePath() + "/" + old);
        File file2 = new File(parentDirectory.getAbsolutePath() + "/" + renameTo);
        if(!file1.exists() || file2.exists()) {
            return false;
        }
        file1.renameTo(file2);
        return true;
    }

    public static boolean renameAllFirstLevelFiles(File targetDirectory) {
        if(targetDirectory.isDirectory()) {
            File[] targetFiles = targetDirectory.listFiles();
            //Security measure in case an incorrect directory with a bunch of files was specified
            if(targetFiles.length < 300) {
                for(File file: targetFiles) {
                    String fileName = file.getName();
                    if(!renameFile(targetDirectory, fileName, StringOperations.camelCaseToSnakeCase(fileName))) {
                        return false;
                    }
                }
            }
            else {
                MessageUtils.showErrorMessage("Target directory cannot contain more than 300 files! This is a security measure.");
                return false;
            }

            return true;
        }
        else {
            MessageUtils.showErrorMessage("Selected file is not a directory!");
            return false;
        }
    }

    public static String getAllFirstLevelFiles(File targetDirectory) {
        String result = "";
        if(targetDirectory.isDirectory()) {
            File[] targetFiles = targetDirectory.listFiles();

            for(File file: targetFiles) {
                String fileName = file.getName();
                int extensionStart = fileName.indexOf('.');
                if(extensionStart != -1) {
                    String fileNameWithoutExtension = fileName.substring(0, extensionStart);
                    result += fileNameWithoutExtension + "\n";
                }
            }

            return result;
        }
        else {
            MessageUtils.showErrorMessage("Selected file is not a directory!");
            return result;
        }
    }

}
