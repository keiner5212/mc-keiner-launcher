package minecraft.client.entities;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import minecraft.client.GUI.Background.Instalation;

public class DummyProgressMonitor implements IProgressMonitor{
    public static JProgressBar progressBar;
    public static JLabel progressLabel;

    public DummyProgressMonitor(JProgressBar progressBar, JLabel progressLabel) {
        DummyProgressMonitor.progressBar = progressBar;
        DummyProgressMonitor.progressLabel = progressLabel;
    }

    @Override
    public void setProgress(int progress) {
        progressBar.setValue(progress);
        progressLabel.setText(progress + " Bytes"+" / "+Instalation.totalSize+" Bytes");
    }

    @Override
    public void setMax(int len) {
        progressBar.setMaximum(len);
        progressBar.setValue(0);
        Instalation.totalSize = len;
    }

    @Override
    public void incrementProgress(int amount) {

    }

    @Override
    public void setStatus(String status) {
        progressLabel.setText(status);
    }
}
