package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.components.CorredorThread;

public class Pista extends Stage {
    private GridPane grid_pista;
    private ProgressBar[] progress_bar_carriles;
    private Button button_iniciar;
    private Scene escena;
    private Label[] label_corredores;
    private CorredorThread[] thread_corredores;
    private VBox vbox;
    private String[] string_corredores = {"Juanpi", "Ian", "juanpa", "Rafa", "Emilio"};
    public Pista() {
        CrearUI();
        this.setTitle("Pista de atletismo");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        label_corredores = new Label[5];
        progress_bar_carriles = new ProgressBar[5];
        grid_pista = new GridPane();
        for (int i = 0; i < 5; i++) {
            label_corredores[i] = new Label(string_corredores[i]);
            grid_pista.add(label_corredores[i],0,i);
            progress_bar_carriles[i] = new ProgressBar(0);
            grid_pista.add(progress_bar_carriles[i],1,i);
        }
        button_iniciar = new Button("Iniciar");
        button_iniciar.setOnAction(actionEvent -> IniciarCarrera());
        vbox = new VBox(grid_pista, button_iniciar);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        escena = new Scene(vbox,200,150);

    }

    private void IniciarCarrera() {
        thread_corredores = new CorredorThread[5];
        for (int i = 0; i < 5; i++) {
            thread_corredores[i] = new CorredorThread(string_corredores[i], progress_bar_carriles[i]);
            thread_corredores[i].start();

        }
    }
}
