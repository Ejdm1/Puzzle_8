import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;

public class Controller {

    @FXML
    private VBox layerList;

    @FXML
    private Label labelOut;

    @FXML
    private Button generate;

    @FXML
    private Button cleanAll;

    @FXML
    private GridPane gridOutput;

    @FXML
    private GridPane gridInput;

    Scene scene;
    Stage primaryStage;
    ArrayList<Node> nodes = new ArrayList<Node>();

    int unsolvedMat[][] = new int[3][3];

    public void initialize() {
        generate.setStyle("-fx-font: 15 arial; -fx-border-color: black; -fx-background-color: transparent");
        cleanAll.setStyle("-fx-font: 15 arial; -fx-border-color: black; -fx-background-color: transparent");
        labelOut.setStyle("-fx-font: 15 arial; -fx-border-color: black;");

        generate.setOnAction(e -> addButton());
        setComboBox();
    }

    public void clearAll() {
        comboSet = false;
        deleteButtons();
        deleteLabels();
        labelOut.setText("");
        first = true;
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    try {
                        gridInput.getChildren().remove(scene.lookup("#comboBoxLable" + String.valueOf(j) + String.valueOf(k)));
                    }
                    catch(Exception e) {
                        return;
                    }
                }
            }
        }
        setComboBox();
    }

    public void setComboBox() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(scene != null && scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)) != null) {
                    gridInput.getChildren().remove((scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j))));
                }
                ComboBox<Integer> comboBox = new ComboBox<Integer>();
                comboBox.setPrefSize(100, 100);
                comboBox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8));
                comboBox.setId("comboBox" + String.valueOf(i) + String.valueOf(j));
                comboBox.setOnAction(e -> showSelected(comboBox.getId()));
                gridInput.add(comboBox, j , i);
                Label label = new Label();
                label.setId("comboBoxLable" + String.valueOf(i) + String.valueOf(j));
                label.setMaxSize(98, 98);
                label.setMinSize(98, 98);
                label.setText("");
                label.setStyle("-fx-font: 24 arial; -fx-background-color: white; -fx-border-color: black;");
                label.setMouseTransparent(true);
                label.setAlignment(Pos.CENTER);
                gridInput.add(label, j, i);
            }
        }
    }

// {1, 2, 3},
// {5, 6, 0},
// {7, 8, 4}

// {2, 5, 3},
// {0, 6, 8},
// {1, 4, 7}

// {8, 4, 5},
// {0, 2, 6},
// {3, 7, 1}

// {5, 1, 2},
// {7, 6, 3},
// {8, 4, 0}

    public void setComboBoxDefault() {
        comboSet = true;
        int unsolvedMat2[][] = {
            {5, 1, 2},
            {7, 6, 3},
            {8, 4, 0}
        };
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ComboBox<Integer> comboBox = (ComboBox<Integer>)(scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)));
                comboBox.setValue(unsolvedMat2[i][j]);
                comboBox.setOnAction(e -> showSelected(comboBox.getId()));
                showSelected("comboBox" + String.valueOf(i) + String.valueOf(j));
            }
        }
    }

    public void showSelected(String id) {
        comboSet = true;
        gridInput.getChildren().remove(scene.lookup("#comboBoxLable" + String.valueOf(id.substring(8, 9)) + String.valueOf(id.substring(9, 10))));
        Label label = new Label();
        label.setId("comboBoxLable" + String.valueOf(id.substring(8, 9)) + String.valueOf(id.substring(9, 10)));
        label.setMaxSize(98, 98);
        label.setMinSize(98, 98);
        label.setText(String.valueOf(((ComboBox<Integer>)(scene.lookup("#" + id))).getValue()));
        label.setStyle("-fx-font: 24 arial; -fx-background-color: white; -fx-border-color: black;");
        label.setAlignment(Pos.CENTER);
        label.setMouseTransparent(true);
        gridInput.add(label, Integer.parseInt(id.substring(9, 10)), Integer.parseInt(id.substring(8, 9)));
    }

    // public void deleteComboBoxLabel(String id) {
    //     System.out.println(id);
    // }
    boolean first = true;
    boolean comboSet = false;
    int solveState = 0; // 1 = unsolvable, 0= solved, -1 = solvable but not by this algorithm
    public void solveMat(int[][] unsolvedMat) {
        int solvedMat[][] = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        if(!comboSet) {
            setComboBoxDefault();
            if(first) {
                System.out.print("Default Mat set");first = false;
            }
        }

        for(int i = 0; i < Root.sizeOfMat; i++) {
            for(int j = 0; j < Root.sizeOfMat; j++) {
                unsolvedMat[i][j] = (int)((ComboBox)(scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)))).getValue();
                if(first) {System.out.print("Input Mat set"); first = false;}
            }
        }
        first = true;
        System.out.println("\n");

        ArrayList<Integer> pomArr = new ArrayList<Integer>();

        for(int i = 0; i < Root.sizeOfMat; i++) {
            for(int j = 0; j < Root.sizeOfMat; j++) {
                if(unsolvedMat[i][j] != 0) {
                    pomArr.add(unsolvedMat[i][j]);
                }
            }
        }

        pomArr.sort(null);
        int inversions = 0;

        for(int i = 0; i < pomArr.size(); i++) {
            for(int j = i+1; j < pomArr.size(); j++) {
                if(pomArr.get(j) == pomArr.get(i)) {
                    inversions++;
                }
            }
        }
        int zero_x = 0, zero_y = 0;

        if(inversions % 2 == 1) {
            System.out.println("Unsolvable");
            solveState = 1;
        }
        else {
            System.out.println("Solvable");
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(unsolvedMat[i][j] == 0) {
                        zero_x = i;
                        zero_y = j;
                    }
                }
            }

            Root root = new Root();
            nodes = root.solve(unsolvedMat, zero_x, zero_y, solvedMat);
            if(nodes != null) {solveState = 0;}
            else if (nodes == null) {solveState = -1;System.out.println("solvable but not by this algorithm");}
        }
    }

    public void solveMatrix() {
        solveMat(unsolvedMat);
    }

    public void addButton() {
        //solveState 1 = unsolvable, 0= solved, -1 = solvable but not by this algorithm
        solveMatrix();
        if(solveState == 1) {labelOut.setText("Unsolvable");return;}
        else if(solveState == -1) {labelOut.setText("Error"); return;}

        labelOut.setText("Solved");
        deleteButtons();

        for(int i = 0; i < nodes.size(); i++) {
            Button button = new Button("Layer: " + String.valueOf(nodes.get(i).layer));
            button.setAlignment(Pos.CENTER);
            button.setPrefWidth(100);
            button.setPrefHeight(layerList.getHeight()/10);
            button.setMinHeight(10);
            button.setMaxHeight(30);
            button.setStyle("-fx-font: 15 arial; -fx-background-color: transparent; -fx-border-color: black");
            button.setId("button" + String.valueOf((i)));
            button.setOnAction(e -> showLayer(button.getId()));
            layerList.getChildren().add(button);
        }
    }

    public void deleteButtons() {
        try {
            for(int i = 0; i < 100; i++) {
                layerList.getChildren().remove(scene.lookup("#button" + String.valueOf(i)));
            }
        }
        catch (Exception e) {}
    }

    public void showLayer(String id) {
        System.out.println("Layer: " + String.valueOf(nodes.get(Integer.parseInt(id.substring(6))).layer));
        printMatrix(nodes.get(Integer.parseInt(id.substring(6))).mat);
    }

    public void printMatrix(int mat[][]) {
        deleteLabels();
        for(int k = 0; k < 3; k++) {
            for(int l = 0; l < 3; l++) {
                Label label = new Label();
                label.setId("label" + String.valueOf(k) + String.valueOf(l));
                label.setMaxSize(200, 200);
                label.setMinSize(200, 200);
                label.setText(String.valueOf(mat[l][k]));
                label.setStyle("-fx-font: 40 arial;");
                label.setAlignment(Pos.CENTER);
                gridOutput.add(label, k, l);
            }
        }
    }

    public void deleteLabels() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                gridOutput.getChildren().remove(scene.lookup("#label" + String.valueOf(i) + String.valueOf(j)));
            }
        }
    }
}
