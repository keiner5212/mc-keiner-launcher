package minecraft.client.GUI.Background;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

import minecraft.client.api.VersionsRequests;

public class VersionsFabric implements Runnable {
    private JComboBox<String> lista;

    public VersionsFabric(JComboBox<String> lista) {
        this.lista = lista;
    }

    @Override
    public void run() {
        ArrayList<HashMap<String, Object>> versions = (ArrayList<HashMap<String, Object>>) VersionsRequests
                .getVersionsFabric();
        lista.removeAllItems();
        for (HashMap<String, Object> version : versions) {
            lista.addItem(version.get("version").toString());
        }
    }
    
}
