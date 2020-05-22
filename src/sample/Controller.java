package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;

import java.awt.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btnSprawdz;
    @FXML
    private Button btnRozpocznij;
    @FXML
    private Button btnDalej;
    ObservableList<Integer> zakresOptions = FXCollections.observableArrayList(10,20,30,40,50,60,70,80,90,100);

    @FXML
    ObservableList<Integer> zakresLiczbaZadan = FXCollections.observableArrayList(10,15,20,25,30,35,40,45,50);

    @FXML
    private ComboBox<Integer> cbZakres = new ComboBox<>();
    @FXML
    private Label labelZad;
    @FXML
    private Label labelDzialanie;
    @FXML
    private ComboBox<Integer> cbLiczbaZadan = new ComboBox<>();
    private int prawidlowyWynik;
    @FXML
    private javafx.scene.control.TextField tfWynik;
    private int prawidloweOdp = 0;
    private int nrZadania = 1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbZakres.setItems(zakresOptions);
        cbZakres.setValue(10);
        cbLiczbaZadan.setValue(10);
        cbLiczbaZadan.setItems(zakresLiczbaZadan);
    }


    public void rozpocznijTest(ActionEvent actionEvent) {
        prawidloweOdp = 0;
        btnRozpocznij.setDisable(true);
        cbZakres.setDisable(true);
        cbLiczbaZadan.setDisable(true);
        rozpocznijRunde();
    }

    public void rozpocznijRunde(){
        labelZad.setText(String.valueOf(nrZadania));
        tfWynik.setText("");
        int x = cbZakres.getValue();
        Pair<Integer, Integer> para = wezLiczby(cbZakres.getValue());
        int l1 = para.getKey();
        int l2 = para.getValue();
        labelDzialanie.setText(l1 + "*" + l2 + "=");
        prawidlowyWynik = l1*l2;
        tfWynik.setVisible(true);
        btnSprawdz.setStyle("fx-background-color: red");
    }

    private Pair<Integer, Integer> wezLiczby(int zakres){
       do {
           int x = (int) (Math.random() * ((zakres - 2) + 1) + 2);
           int y = (int) (Math.random() * ((zakres - 2) + 1) + 2);
           int res = x * y;
           if(res <= zakres) return new Pair<Integer, Integer>(x, y);
       }
       while(true);
    }

    public void sprawdzWynik(ActionEvent actionEvent) {
        tfWynik.setDisable(true);
        btnSprawdz.setDisable(true);
        btnDalej.setDisable(false);
        if(prawidlowyWynik == Integer.parseInt(tfWynik.getText())){
            prawidloweOdp++;
            tfWynik.setStyle("-fx-text-fill: blue;");
        } else {
            tfWynik.setStyle("-fx-text-fill: red;");
        }
    }

    public void dalej(ActionEvent actionEvent) {
        btnDalej.setDisable(true);
        if(nrZadania == cbLiczbaZadan.getValue()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Zdobyłeś: " + prawidloweOdp + " punktów!", ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            System.exit(0);
        } else {
            tfWynik.setDisable(false);
            tfWynik.setStyle("-fx-text-fill: black;");
            nrZadania++;
            rozpocznijRunde();
        }
    }


    public void checkValue(KeyEvent keyEvent) {
        if(tfWynik.getText().trim().isEmpty()){
            btnSprawdz.setDisable(true);
        } else {
            btnSprawdz.setDisable(false);
        }
    }
}
