package minecraft.client.persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class FileManager {

    public static void saveData(String filePath, JSONObject jsonData) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jsonData.toJSONString(JSONStyle.LT_COMPRESS));
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
            return null;
        }
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        try {
            return (JSONObject) parser.parse(data.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray loadDataArray(String filePath) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Not found: " + filePath);
            return null;
        }
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        try {
            return (JSONArray) parser.parse(data.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
