package minecraft.client.GUI;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger extends java.util.logging.Logger {
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public Logger(JScrollPane scrollPane, JTextArea textArea) {
        super("Logger", null);
        this.scrollPane = scrollPane;
        this.textArea = textArea;
    }

    public void log(String message) {
        textArea.append(message + "\n");
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    @Override
    public void info(String message) {
        log(message);
    }

    public void clear() {
        textArea.setText("");
    }

    @Override
    public void finest(String message) {
        log(message);
    }

    @Override
    public void fine(String message) {
        log(message);
    }
}
