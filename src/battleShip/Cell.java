package battleShip;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Cell extends Pane {
	int token;

	public int getToken() {
		return token;
	}

	public Cell() {
		token = 1;
		setStyle("-fx-border-color: black");
		this.setPrefSize(800, 800);
		this.setOnMouseClicked(e -> {
			if (!BattleShip.gameStarted)
				handleMouseClickPreGame();
			else
				handleMouseClickDuringGame();
		});
	}

	public void placeShip() {
		Ellipse ellipse = new Ellipse(30, 30, 20, 20);
		ellipse.setStroke(Color.BLACK);
		this.getChildren().add(ellipse);
		token = 2;
	}

	public void destroyShip() {
		Ellipse ellipse = new Ellipse(30, 30, 20, 20);
		ellipse.setStroke(Color.RED);
		ellipse.setFill(Color.RED);
		this.getChildren().add(ellipse);
		BattleShip.lblStatus.setText("Pogodak.");
		token = 3;
	}

	public void failShip() {
		Ellipse ellipse = new Ellipse(30, 30, 20, 20);
		ellipse.setStroke(Color.BLUE);
		ellipse.setFill(Color.BLUE);
		this.getChildren().add(ellipse);
		BattleShip.lblStatus.setText("Promasaj.");
		token = 4;
	}

	/** Handle a mouse click event */
	private void handleMouseClickDuringGame() {
		if (token == 1)
			failShip();
		else if (token == 2)
			destroyShip();
		else if (token == 3)
			;
		else if (token == 4)
			;

		if (Board.isWon(BattleShip.boardComp.board)) {
			System.out.println("Pobjeda");
			System.exit(0);
		}

	}
	
	/** Handle a mouse click event */
	private void handleMouseClickPreGame() {
		if (token == 1)
			placeShip();
		else if (token == 2);
	}

}