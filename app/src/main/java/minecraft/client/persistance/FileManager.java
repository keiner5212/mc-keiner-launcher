package minecraft.client.persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class FileManager {

    public static void saveData(String filePath, JSONObject jsonData) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();  
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jsonData.toString(4)); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject loadData(String filePath) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Not found: " + filePath);
        }
        return new JSONObject(data.toString());
    }
}
