package minecraft.client.GUI;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger {
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JProgressBar progressBar;
    private JLabel progressLabel;

    public Logger(JScrollPane scrollPane, JTextArea textArea, JProgressBar progressBar, JLabel progressLabel) {
        this.progressBar = progressBar;
        this.progressLabel = progressLabel;
        this.scrollPane = scrollPane;
        this.textArea = textArea;
    }

    public void log(String message) {
        textArea.append(message + "\n");
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    public void progress(double progress, double total) {
        double percentage = (progress / total) * 100;
        percentage = Math.round(percentage * 100) / 100.0;
        progressBar.setValue((int) (percentage));
        progress = Math.round(progress/1000000 * 100) / 100.0;
        total = Math.round(total/1000000 * 100) / 100.0;
        progressLabel.setText(percentage + "%" + " - " + progress + "MB / " + total+"MB");
    }
}
