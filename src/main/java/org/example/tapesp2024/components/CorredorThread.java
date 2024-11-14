package org.example.tapesp2024.components;

import javafx.scene.control.ProgressBar;

public class CorredorThread extends Thread{
    String name;
    private ProgressBar progressBar_corredor;

    public CorredorThread(String name, ProgressBar progressBar_corredor){
        super(name);
        this.progressBar_corredor = progressBar_corredor;
    }
    @Override
    public void run() {
        super.run();
        double avance = 0;
        while (avance <= 1) {
            avance += Math.random()/10;
            try {
                Thread.sleep((long)(Math.random()*2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.progressBar_corredor.setProgress(avance);
        }
        System.out.println(this.getName() + " esta en la meta");
    }
}
