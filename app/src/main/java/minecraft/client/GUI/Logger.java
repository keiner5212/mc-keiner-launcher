package minecraft.client.GUI;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger {
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public Logger(JScrollPane scrollPane, JTextArea textArea) {
        this.scrollPane = scrollPane;
        this.textArea = textArea;
    }

    public void log(String message) {
        textArea.append(message + "\n");
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }
}
