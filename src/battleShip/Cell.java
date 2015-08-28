package battleShip;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Cell extends Pane {
	int token;
	int i;
	int j;

	public int getToken() {
		return token;
	}

	public Cell(int i, int j) {
		this.i = i;
		this.j = j;
		token = 1;
		setStyle("-fx-border-color: black");
		this.setPrefSize(800, 800);

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			// Override the handle method
			public void handle(MouseEvent e) {

				if (!BattleShip.gameStarted)
					handleMouseClickPreGame(e);
				else
					handleMouseClickDuringGame();

			}
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
			BattleShip.lblStatus.setText("Korisnik je pobjedio");
		}
		
		enemysShot();
		
		if(Board.isWon(BattleShip.boardUser.board)){
			BattleShip.lblStatus.setText("Kompjuter je pobjedio");
		}

	}
	
	public static void enemysShot(){
		
		int i,j;
		
		do{
		i = (int)(Math.random() * 10);
		j = (int)(Math.random() * 10);
		}while(BattleShip.boardUser.board[i][j].getToken() == 3
				|| BattleShip.boardUser.board[i][j].getToken() == 4);
		
		
		if(BattleShip.boardUser.board[i][j].getToken() == 1)
			BattleShip.boardUser.board[i][j].failShip();
		else if(BattleShip.boardUser.board[i][j].getToken() == 2)
			BattleShip.boardUser.board[i][j].destroyShip();
	}

	/** Handle a mouse click event */
	private void handleMouseClickPreGame(MouseEvent e) {

		int shipLength = BattleShip.userShipLength;
		
		if (shipLength > 0) {
			if (e.getButton() == MouseButton.PRIMARY) { // vertikalno

				int i = this.i;
				int j = this.j;

				boolean isFree = true;

				for (int k = j; k < j + shipLength; k++) {
					if (j + shipLength > 10) {
						isFree = false;
					} else if (BattleShip.boardUser.board[i][k].token != 1) {
						isFree = false;
					}
				}

				if (isFree) {
					for (int k = j; k < j + shipLength; k++) {
						BattleShip.boardUser.board[i][k].placeShip();
						BattleShip.boardUser.board[i][k].token = 2;
					}

				}
			}

			else if (e.getButton() == MouseButton.SECONDARY) { // horizontalno

				int i = this.i;
				int j = this.j;


				boolean isFree = true;

				for (int k = i; k < i + shipLength; k++) {
					if (j + shipLength > 10) {
						isFree = false;
					} else if (BattleShip.boardUser.board[k][j].token != 1) {
						isFree = false;
					}
				}

				if (isFree) {
					for (int k = i; k < i + shipLength; k++) {
						BattleShip.boardUser.board[k][j].placeShip();
						BattleShip.boardUser.board[k][j].token = 2;
					}

				}
			}
		}
		BattleShip.userShipLength--;

	}
}