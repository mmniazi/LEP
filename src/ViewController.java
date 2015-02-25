/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.*;

/**
 * @author muhammad
 */
public class ViewController implements Initializable {

    ObservableList<Strata> strataList;
    ToggleGroup caseGroup;
    ToggleGroup systemGroup;

    @FXML
    private TextField slopeField;

    @FXML
    private RadioButton fpsRadioButton;

    @FXML
    private TextField heightOfWaterField;

    @FXML
    private TextField noOfStrataField;

    @FXML
    private TableView<Strata> strataTable;

    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private RadioButton passiveRadioButton;

    @FXML
    private RadioButton restRadioButton;

    @FXML
    private RadioButton siRadioButton;

    @FXML
    private TextField thetaField;

    @FXML
    private TextField OCRField;

    @FXML
    private Label rankineResult;

    @FXML
    private Label restResult;

    @FXML
    private Label coulombResult;

    @FXML
    private Label differenceLabel;

    @FXML
    private Pane activePassivePane;

    @FXML
    private Pane restPane;

    @FXML
    private TableColumn<Strata, Number> eUnitWeightColumn;

    @FXML
    private TableColumn<Strata, Number> heightColumn;

    @FXML
    private TableColumn<Strata, Boolean> saturatedColumn;

    @FXML
    private TableColumn<Strata, Number> frictionAngleColumn;

    @FXML
    private TableColumn<Strata, Number> cohesionColumn;

    @FXML
    private TableColumn<Strata, Number> noColumn;

    @FXML
    void showResultButtonController(ActionEvent event) {

        double lep = 0;
        double rankineLep = 0;
        double coulombLep = 0;

        double unitWeightWater;
        if (siRadioButton.isSelected()) {
            unitWeightWater = 9.81;
        } else {
            unitWeightWater = 62.4;
        }

        double delta = (2.0 / 3.0) * strataList.stream()
                .mapToDouble(Strata::getfrictionAngle)
                .average()
                .getAsDouble();
        double thetta = Double.valueOf(thetaField.getText()) * 0.017453292;
        double slope = Double.valueOf(slopeField.getText()) * 0.017453292;
        double heightOfWater = Double.valueOf(heightOfWaterField.getText());


        strataList.forEach((Strata strata) -> {
            strata.setUnitWeightWater(unitWeightWater);
            if (activeRadioButton.isSelected()) {
                if (strata.getsaturated()) strata.setcohesion(0);
                double kCoulomb = (pow(cos(strata.getfrictionAngle() - thetta), 2))
                        / (pow(cos(thetta), 2) * cos(delta + thetta)
                        * pow((1 + sqrt((sin(delta + strata.getfrictionAngle()) * sin(strata.getfrictionAngle() - slope))
                        / (cos(delta + thetta) * cos(thetta - slope)))), 2));
                double kRankine = (slope != 0 && strata.getcohesion() != 0) ?
                        ((1 / pow(cos(strata.getfrictionAngle()), 2))
                                * (2 * pow(cos(slope), 2)
                                + 2 * ((strata.getcohesion() / (strata.geteUnityWeight() * strata.getheight())) * cos(strata.getfrictionAngle()) * sin(strata.getfrictionAngle()))
                                - sqrt(4 * pow(cos(slope), 2) * (pow(cos(slope), 2) - pow(cos(strata.getfrictionAngle()), 2))
                                + 4 * pow(strata.getcohesion() / (strata.geteUnityWeight() * strata.getheight()), 2) * pow(cos(strata.getfrictionAngle()), 2)
                                + 8 * (strata.getcohesion() / (strata.geteUnityWeight() * strata.getheight())) * pow(cos(slope), 2)
                                * sin(strata.getfrictionAngle()) * cos(strata.getfrictionAngle()))) - 1)
                        : (cos(slope)
                        * ((cos(slope) - sqrt(pow(cos(slope), 2) - pow(cos(strata.getfrictionAngle()), 2)))
                        / (cos(slope) + sqrt(pow(cos(slope), 2) - pow(cos(strata.getfrictionAngle()), 2)))));
                strata.setkCoulomb(kCoulomb);
                strata.setkRankine(kRankine);
            } else if (passiveRadioButton.isSelected()) {
                double kCoulomb = (pow(cos(strata.getfrictionAngle() + thetta), 2))
                        / (pow(cos(thetta), 2) * cos(delta - thetta)
                        * pow((1 - sqrt((sin(delta + strata.getfrictionAngle()) * sin(strata.getfrictionAngle() + slope))
                        / (cos(delta - thetta) * cos(slope - thetta)))), 2));
                double kRankine = (slope != 0 && strata.getcohesion() != 0) ?
                        ((1 / pow(cos(strata.getfrictionAngle()), 2))
                                * (2 * pow(cos(slope), 2)
                                + 2 * ((strata.getcohesion() / (strata.geteUnityWeight() * strata.getheight())) * cos(strata.getfrictionAngle()) * sin(strata.getfrictionAngle()))
                                + sqrt(4 * pow(cos(slope), 2) * (pow(cos(slope), 2) - pow(cos(strata.getfrictionAngle()), 2))
                                + 4 * pow(strata.getcohesion() / (strata.geteUnityWeight() * strata.getheight()), 2) * pow(cos(strata.getfrictionAngle()), 2)
                                + 8 * (strata.getcohesion() / (strata.geteUnityWeight() * strata.getheight())) * pow(cos(slope), 2)
                                * sin(strata.getfrictionAngle()) * cos(strata.getfrictionAngle()))) - 1)
                        : (cos(slope)
                        * ((cos(slope) + sqrt(pow(cos(slope), 2) - pow(cos(strata.getfrictionAngle()), 2)))
                        / (cos(slope) - sqrt(pow(cos(slope), 2) - pow(cos(strata.getfrictionAngle()), 2)))));
                strata.setkCoulomb(kCoulomb);
                strata.setkRankine(kRankine);
            } else {
                double OCR = Double.valueOf(OCRField.getText());
                double k = 1 - sin(strata.getfrictionAngle()) * pow(OCR, sin(strata.getfrictionAngle()));
                strata.setk(k);
            }
        });

        if (activeRadioButton.isSelected()) {
            // Rankine
            for (int i = 0; i < strataList.size(); i++) {
                for (int j = 0; j < i; j++) {
                    rankineLep += strataList.get(j).geteUnityWeight() * strataList.get(j).getheight() * strataList.get(i).getheight() * strataList.get(i).getkRankine()
                            - 2 * strataList.get(i).getcohesion() * strataList.get(j).getheight() * sqrt(strataList.get(i).getkRankine());
                }
                rankineLep += 0.5 * strataList.get(i).geteUnityWeight() * pow(strataList.get(i).getheight(), 2) * strataList.get(i).getkRankine()
                        - 2 * strataList.get(i).getcohesion() * strataList.get(i).getheight() * sqrt(strataList.get(i).getkRankine());
            }
            // Coulomb
            for (int i = 0; i < strataList.size(); i++) {
                for (int j = 0; j < i; j++) {
                    coulombLep += strataList.get(j).geteUnityWeight() * strataList.get(j).getheight() * strataList.get(i).getheight() * strataList.get(i).getkCoulomb()
                            - 2 * strataList.get(i).getcohesion() * strataList.get(j).getheight() * sqrt(strataList.get(i).getkCoulomb());
                }
                coulombLep += 0.5 * strataList.get(i).geteUnityWeight() * pow(strataList.get(i).getheight(), 2) * strataList.get(i).getkCoulomb()
                        - 2 * strataList.get(i).getcohesion() * strataList.get(i).getheight() * sqrt(strataList.get(i).getkCoulomb());
            }

        } else if (passiveRadioButton.isSelected()) {
            // Rankine
            for (int i = 0; i < strataList.size(); i++) {
                for (int j = 0; j < i; j++) {
                    rankineLep += strataList.get(j).geteUnityWeight() * strataList.get(j).getheight() * strataList.get(i).getheight() * strataList.get(i).getkRankine()
                            + 2 * strataList.get(i).getcohesion() * strataList.get(j).getheight() * sqrt(strataList.get(i).getkRankine());
                }
                rankineLep += 0.5 * strataList.get(i).geteUnityWeight() * pow(strataList.get(i).getheight(), 2) * strataList.get(i).getkRankine()
                        + 2 * strataList.get(i).getcohesion() * strataList.get(i).getheight() * sqrt(strataList.get(i).getkRankine());
            }
            // Coulomb
            for (int i = 0; i < strataList.size(); i++) {
                for (int j = 0; j < i; j++) {
                    coulombLep += strataList.get(j).geteUnityWeight() * strataList.get(j).getheight() * strataList.get(i).getheight() * strataList.get(i).getkCoulomb()
                            - 2 * strataList.get(i).getcohesion() * strataList.get(j).getheight() * sqrt(strataList.get(i).getkCoulomb());
                }
                coulombLep += 0.5 * strataList.get(i).geteUnityWeight() * pow(strataList.get(i).getheight(), 2) * strataList.get(i).getkCoulomb()
                        - 2 * strataList.get(i).getcohesion() * strataList.get(i).getheight() * sqrt(strataList.get(i).getkCoulomb());
            }
        } else {
            // Rest
            for (int i = 0; i < strataList.size(); i++) {
                for (int j = 0; j < i; j++) {
                    lep += strataList.get(j).geteUnityWeight() * strataList.get(j).getheight() * strataList.get(i).getheight() * strataList.get(i).getk();
                }
                lep += 0.5 * strataList.get(i).geteUnityWeight() * pow(strataList.get(i).getheight(), 2) * strataList.get(i).getk();
            }
        }

        rankineLep += 0.5 * unitWeightWater * pow(heightOfWater, 2);
        coulombLep += 0.5 * unitWeightWater * pow(heightOfWater, 2);
        lep += 0.5 * unitWeightWater * pow(heightOfWater, 2);
        if (!restRadioButton.isSelected()) {
            restPane.setVisible(false);
            activePassivePane.setVisible(true);
            coulombResult.setText(String.format("%.1f",coulombLep));
            rankineResult.setText(String.format("%.1f", rankineLep));
            differenceLabel.setText("Difference: "+String.format("%.1f", abs(coulombLep - rankineLep)));
        } else {
            activePassivePane.setVisible(false);
            restPane.setVisible(true);
            restResult.setText(String.format("%.1f",lep));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        strataTable.setEditable(true);
        strataList = FXCollections.observableArrayList();

        caseGroup = new ToggleGroup();
        systemGroup = new ToggleGroup();

        activeRadioButton.setToggleGroup(caseGroup);
        passiveRadioButton.setToggleGroup(caseGroup);
        restRadioButton.setToggleGroup(caseGroup);

        siRadioButton.setToggleGroup(systemGroup);
        fpsRadioButton.setToggleGroup(systemGroup);

        restRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            OCRField.setDisable(!newValue);
        });

        eUnitWeightColumn.setCellValueFactory(cellData -> cellData.getValue().eUnityWeightProperty());
        cohesionColumn.setCellValueFactory(cellData -> cellData.getValue().cohesionProperty());
        frictionAngleColumn.setCellValueFactory(cellData -> cellData.getValue().frictionAngleProperty());
        heightColumn.setCellValueFactory(cellData -> cellData.getValue().heightProperty());
        noColumn.setCellValueFactory(cellData -> cellData.getValue().noProperty());
        saturatedColumn.setCellValueFactory(cellData -> cellData.getValue().saturatedProperty());

        StringConverter<Number> converter = new StringConverter<Number>() {

            @Override
            public String toString(Number object) {
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                return new Double(string);
            }
        };

        eUnitWeightColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        eUnitWeightColumn.setOnEditCommit((TableColumn.CellEditEvent<Strata, Number> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).seteUnityWeight((double) event.getNewValue());
        });
        cohesionColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        cohesionColumn.setOnEditCommit((TableColumn.CellEditEvent<Strata, Number> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setcohesion((double) event.getNewValue());
        });
        frictionAngleColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        frictionAngleColumn.setOnEditCommit((TableColumn.CellEditEvent<Strata, Number> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setfrictionAngle((double) event.getNewValue());
        });
        heightColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        heightColumn.setOnEditCommit((TableColumn.CellEditEvent<Strata, Number> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setheight((double) event.getNewValue());
        });
        saturatedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(saturatedColumn));
        saturatedColumn.setOnEditCommit((TableColumn.CellEditEvent<Strata, Boolean> event) -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setsaturated(event.getNewValue());
        });

        strataTable.setItems(strataList);
    }

    @FXML
    private void noOfStrataController(ActionEvent event) {
        strataList.clear();
        int noOfStrata = Integer.valueOf(noOfStrataField.getText());
        for (int i = 1; i <= noOfStrata; i++) {
            strataList.add(new Strata(i));
        }
    }

}
