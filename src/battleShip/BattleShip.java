package battleShip;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BattleShip extends Application{
	
	public static int userShipLength = 5;
	
	public static Label lblStatus = new Label("Igraj posteno.");
	public static Button btStart = new Button("Start");
	
	public static Board boardUser;
	public static Board boardComp;
	
	public static boolean gameStarted = false;
	
	public void start(Stage primaryStage){
		
		
		boardUser = new Board();
		
		boardComp = new Board();
		boardComp.placeEnemyShips();
		
		
		BorderPane paneEnemy = new BorderPane();
		paneEnemy.setCenter(boardComp);
		paneEnemy.setBottom(lblStatus);
		
		BorderPane paneUser = new BorderPane();
		paneUser.setCenter(boardUser);
		paneUser.setBottom(btStart);
		
		Scene scene = new Scene(paneEnemy, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Enemy board");
		primaryStage.show();
		
		
		Stage secondaryStage = new Stage();
		Scene scene2 = new Scene(paneUser, 600, 600);
		secondaryStage.setScene(scene2);
		secondaryStage.setTitle("User sboard");
		secondaryStage.show();
		
		btStart.setOnAction(e ->
		gameStarted = true);
		
	}
	
	

	public static void main(String[] args) {
		
		Application.launch(args);

	}
	
	

}
