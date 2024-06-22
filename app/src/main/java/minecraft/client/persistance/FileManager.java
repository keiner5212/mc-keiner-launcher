package minecraft.client.persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class FileManager {

    public static void guardarDatos(String filePath, JSONObject jsonData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonData.toString(4)); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject cargarDatos(String filePath) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(data.toString());
    }
}
