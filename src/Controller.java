import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;

import javafx.util.Duration;

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

    @FXML
    private TextField combinationInput;

    @FXML
    private Button setComboBoxDefault;

    Scene scene;
    Stage primaryStage;
    ArrayList<Node> nodes = new ArrayList<Node>();
    boolean wrongCombination = true;

    int unsolvedMat[][] = {{9,9,9},{9,9,9},{9,9,9}};

    public void initialize() {
        setComboBoxDefault.setStyle("-fx-font: 15 arial; -fx-border-color: black; -fx-background-color: transparent");
        combinationInput.setStyle("-fx-font: 15 arial; -fx-border-color: black; -fx-background-color: transparent");
        combinationInput.setOnKeyTyped(event -> updateMatrix(1));
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
        combinationInput.setText("");
        first = true;
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    unsolvedMat[j][k] = 9;
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
                comboBox.setStyle("-fx-font: 20 arial; -fx-border-color: black; -fx-background-color: transparent");
                comboBox.setPrefSize(100, 100);
                comboBox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8));
                comboBox.setId("comboBox" + String.valueOf(i) + String.valueOf(j));
                comboBox.setOnAction(event -> updateMatrix(0));
                gridInput.add(comboBox, j , i);
            }
        }
    }

    public void updateMatrix(int comboOrText) {
        if(comboOrText == 0) {
            boolean isSet = true;
            for(int i = 0; i < Root.sizeOfMat; i++) {
                for(int j = 0; j < Root.sizeOfMat; j++) {
                    if(((ComboBox)(scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)))).getValue() != null) {
                        unsolvedMat[i][j] = (int)((ComboBox)(scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)))).getValue();
                    }
                    else {
                        isSet = false;
                    }
                }
            }
            if(isSet) {
                comboSet = true;
            }
        }
        else if(comboOrText == 1) {
            int counter = 0;
            String input = combinationInput.getText();
            for(int i = 0; i < Root.sizeOfMat; i++) {
                for(int j = 0; j < Root.sizeOfMat; j++) {
                    if(input.length() > counter) {
                        unsolvedMat[i][j] = input.charAt(counter) - '0';
                        ((ComboBox)(scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)))).setValue(input.charAt(counter) - '0');
                    }
                    counter++;
                }
            }
            if(input.length() == 9) {
                comboSet = true;
            }
        }
        matrixTest();
        int counter = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(unsolvedMat[i][j] + ", ");
                counter++;
                if(counter == 3) {System.out.println(); counter = 0;}
            }
        }
    }

    public void matrixTest() {
        int counter = 0;
        boolean wrong = false;
        int comboValues[] = {0,1,2,3,4,5,6,7,8};
        for(int k = 0; k < 9; k++) {
            int amount = 0;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(unsolvedMat[i][j] == comboValues[counter]) {
                        amount++;
                    }
                    if(unsolvedMat[i][j] == 9) {
                        wrong = true;
                    }
                }
            }
            if(amount > 1 || amount == 0) {
                wrong = true;
            }
            counter++;
        }
        if(wrong) {wrongCombination = true; labelOut.setText("Wrong combination");}
        else {wrongCombination = false; labelOut.setText("");}
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
        int unsolvedMat2[][] = {
            {5, 1, 2},
            {7, 6, 3},
            {8, 4, 0}
        };
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ComboBox<Integer> comboBox = (ComboBox<Integer>)(scene.lookup("#comboBox" + String.valueOf(i) + String.valueOf(j)));
                comboBox.setValue(unsolvedMat2[i][j]);
            }
        }
        comboSet = true;
        wrongCombination = false;
        combinationInput.setText("");
    }

    // public void deleteComboBoxLabel(String id) {
    //     System.out.println(id);
    // }
    boolean first = true;
    boolean comboSet = false;
    int solveState = 0; // 1 = unsolvable, 0= solved, -1 = solvable but not by this algorithm
    public void solveMat() {
        int solvedMat[][] = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

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
        solveMat();
    }

    public void addButton() {
        if(!wrongCombination) {
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
