package minecraft.client.GUI.Background;

import java.util.ArrayList;

import javax.swing.JComboBox;

import minecraft.client.api.VersionsRequests;

public class VersionsForge implements Runnable {
    private JComboBox<String> lista;
    private String SelectedVersion;

    public VersionsForge(JComboBox<String> lista, String SelectedVersion) {
        this.lista = lista;
        this.SelectedVersion = SelectedVersion;
    }

    @Override
    public void run() {
        ArrayList<String> versions = new ArrayList<String>(VersionsRequests.getVersionsForge()); 
        lista.removeAllItems();
        for (String element : versions) {
            if (element.startsWith(SelectedVersion)) {
                lista.addItem(element);
            }
        }
    }

}
