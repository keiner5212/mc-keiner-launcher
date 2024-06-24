
package minecraft.client.GUI.Background;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

import minecraft.client.api.VersionsRequests;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author keiner5212
 */
public class VersionsVanilla implements Runnable {
    private JComboBox<String> lista;
    private String type;

    public VersionsVanilla(JComboBox<String> lista, String type) {
        this.lista = lista;
        this.type = type;
    }

    @Override
    public void run() {
        JSONArray versions = VersionsRequests
                .getVersionsVanilla();
        lista.removeAllItems();
        switch (type) {
            case "Any":
                for (Object elem : versions) {
                    JSONObject version = (JSONObject) elem;
                    lista.addItem(version.get("id").toString() + " (" + version.get("type").toString() + ")");
                }
                break;

            case "Release":
                for (Object elem : versions) {
                    JSONObject version = (JSONObject) elem;
                    if (version.get("type").toString().equals("release")) {
                        lista.addItem(version.get("id").toString());
                    }
                }
                break;

            case "Snapshot":
                for (Object elem : versions) {
                    JSONObject version = (JSONObject) elem;
                    if (version.get("type").toString().equals("snapshot")) {
                        lista.addItem(version.get("id").toString());
                    }
                }
                break;

            case "Other":
                for (Object elem : versions) {
                    JSONObject version = (JSONObject) elem;
                    if (!version.get("type").toString().equals("release")
                            && !version.get("type").toString().equals("snapshot")) {
                        lista.addItem(version.get("id").toString() + " (" + version.get("type").toString() + ")");
                    }
                }
                break;

            default:
                break;
        }

    }

}
