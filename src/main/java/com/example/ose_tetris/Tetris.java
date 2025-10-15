package com.example.ose_tetris;

import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {

    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int [][] MESH = new int[XMAX/SIZE][YMAX/SIZE];
    private static Pane groupe = new Pane();
    private static Form object;
    private static Scene scene = new Scene(groupe, XMAX + 150, YMAX);
    public static int score = 0;
    public static int top = 0;
    private static boolean game = true;
    private static Form nextObj = Controller.makeRect();
    private static int linesNo = 0;

    public void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        for (int[] a: MESH){
            Arrays.fill(a,0);
        }

        Line line = new Line(XMAX, 0, XMAX, YMAX);

        Text scoreText = new Text("Score: ");
        scoreText.setStyle("-fx-font: 20 arials;");
        scoreText.setY(50);
        scoreText.setX(XMAX + 5);

        Text levelText = new Text("Level: ");
        levelText.setStyle("-fx-font: 20 arials;");
        scoreText.setY(100);
        scoreText.setX(XMAX + 5);
        levelText.setFill(Color.GREEN);
        groupe.getChildren().addAll(scoreText, line, levelText);

        Form a = nextObj;
        groupe.getChildren().addAll(a.a, a.b, a.c, a.d);
        moveOnKeyPress(a);
        object = a;
        nextObj = Controller.makeRect();

        stage.setScene(scene);
        stage.setTitle("TETRIS");
        stage.show();

        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable(){
                    public void run(){
                        if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0){
                            top++;
                        } else{
                            top = 0;
                        }

                        if (top == 2){
                            Text over = new Text("Game over!");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arials;");
                            over.setY(250);
                            over.setX(10);
                            groupe.getChildren().add(over);
                            game = false;
                        }

                        if (top == 15){
                            System.exit(0);
                        }

                        if (game){
                            MoveDown(object);
                            scoreText.setText("Score: " + Integer.toString(score));
                            levelText.setText("Level: " + Integer.toString(linesNo));
                        }
                    }
                });
            }
        };
        fall.schedule(task, 0, 300);
    }

    private void moveOnKeyPress(Form form){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
                switch (e.getCode()){
                    case RIGHT:
                        Controller.MoveRight(form);
                        break;
                    case LEFT:
                        Controller.MoveLeft(form);
                    case DOWN:
                        MoveDown(form);
                        score++;
                        break;
                    case UP:
                        MoveTurn(form);
                        break;
                }
            }
        });
    }
}
