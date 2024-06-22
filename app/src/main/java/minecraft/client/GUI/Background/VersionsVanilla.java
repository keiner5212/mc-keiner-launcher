
package minecraft.client.GUI.Background;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

import minecraft.client.api.VersionsRequests;

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
        System.out.println("actualizando versiones vanilla");
        ArrayList<HashMap<String, Object>> versions = (ArrayList<HashMap<String, Object>>) VersionsRequests
                .getVersionsVanilla();
        lista.removeAllItems();
        switch (type) {
            case "Any":
                for (HashMap<String, Object> version : versions) {
                    lista.addItem(version.get("id").toString() + " - " + version.get("type").toString());
                }
                break;

            case "Release":
                for (HashMap<String, Object> version : versions) {
                    if (version.get("type").toString().equals("release")) {
                        lista.addItem(version.get("id").toString());
                    }
                }
                break;

            case "Snapshot":
                for (HashMap<String, Object> version : versions) {
                    if (version.get("type").toString().equals("snapshot")) {
                        lista.addItem(version.get("id").toString());
                    }
                }
                break;

            case "Other":
                for (HashMap<String, Object> version : versions) {
                    if (!version.get("type").toString().equals("release")
                            && !version.get("type").toString().equals("snapshot")) {
                        lista.addItem(version.get("id").toString() + " - " + version.get("type").toString());
                    }
                }
                break;

            default:
                break;
        }

    }

}
