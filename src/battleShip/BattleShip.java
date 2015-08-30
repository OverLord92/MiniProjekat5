package battleShip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/** Igrica BattleShip */

public class BattleShip extends Application {

	// statik varijabla koja se koristi pri posljavljanju brodva igraca
	public static int userShipLength = 5;

	// label koji pokazuje informacije o igri i trenutno stanje
	public static Label lblStatus = new Label("\tLijevim klikom na polje "
			+ "brod postavljate vertikalno, desnim klikom horiznostalno.\n"
			+ "\tNakon postavljanja brodova kliknite na start.");

	// dugme za koje igrac klika poslije postavljanja brodova kako bi zapoceo
	// igru
	public static Button btStart = new Button("Start");

	// dugme koje korisnik klika ukoliko zeli resetovati igru ---------- treba
	// implementirati
	public static Button btReset = new Button("Reset");

	// ploca za igracevim brodovima
	public static Board boardUser;

	// plova za brodovima kompkjutera
	public static Board boardComp;

	// tretnuno stanje igre
	// gameStarted == false ---> korisnik postavlja brodove
	// gameStarted == true ----> igra pocinje
	public static boolean gameStarted = false;

	public void start(Stage primaryStage) {

		// generisanje korisnicke ploce
		boardUser = new Board();

		// generisanje ploce kompjutera i postavljanje bordova kompjutera
		boardComp = new Board();
		boardComp.placeEnemyShips();

		lblStatus.setPrefSize(490, 40);

		BorderPane paneEnemy = new BorderPane();
		paneEnemy.setCenter(boardComp);
		paneEnemy.setBottom(lblStatus);

		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10, 10, 10, 10));
		hBox.getChildren().addAll(btStart, lblStatus, btReset);

		BorderPane paneUser = new BorderPane();
		paneUser.setCenter(boardUser);
		paneUser.setBottom(hBox);

		// Stage pomocu kojeg prikazujemo protivnicku plocu
		Scene scene = new Scene(paneEnemy, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Enemy board");
		primaryStage.show();

		// Stage pomocu kojeg prestavljamo igracevu plocu
		Stage secondaryStage = new Stage();
		Scene scene2 = new Scene(paneUser, 600, 600);
		secondaryStage.setScene(scene2);
		secondaryStage.setTitle("User sboard");
		secondaryStage.show();

		// klikom na dugme "start" igra pocinje
		btStart.setOnAction(e ->{ 
		gameStarted = true;
		lblStatus.setText("\tIgra je pocela. Pritiskom na dugme reset restartujete igru.");
		});

		// klikom na dugme "reset" igra se resetuje
		btReset.setOnAction(e -> Board.resetGame());

	}

	public static void main(String[] args) {

		Application.launch(args);

	}

}
