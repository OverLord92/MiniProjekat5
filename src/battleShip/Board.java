package battleShip;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Board extends GridPane {
	Cell[][] board = new Cell[10][10];

	public void placeEnemyShips() {
		int shipLength = 5;

		do {
			// ako je horizontal == 1 brod se postavlja horizontalno
			// ako je horizontak == 0 brod se postavlja vertikalno
			
			int horizontal = (int) (Math.random() * 2);
		
			
			if (horizontal == 1) {

				int i, j;

				boolean isFree = false;
				do {
					isFree = true;
					i = (int) (Math.random() * 10);

					j = (int) (Math.random() * (10 - shipLength));

					for (int k = j; k < j + shipLength; k++) {
						if (board[i][k].token != 1) {
							isFree = false;
						}
					}

				} while (!isFree);

				for (int k = j; k < j + shipLength; k++) {
					// board[i][k].placeShip();
					board[i][k].token = 2;
				}
			} else if (horizontal == 0) {

				int i, j;

				boolean isFree = false;
				do {
					isFree = true;
					i = (int) (Math.random() * (10 - shipLength));

					j = (int) (Math.random() * 10);

					for (int k = i; k < i + shipLength; k++) {
						if (board[k][j].token != 1) {
							isFree = false;
						}
					}
				} while (!isFree);

				for (int k = i; k < i + shipLength; k++) {
					board[k][j].token = 2;
				}
			}

			shipLength--;
		} while (shipLength > 0);

	}
	
	public void userPlaceShips(){
		int shipLength = 5;
		
		
		
//		BattleShip.gameStarted = true;
	}
	
	
	

	Board() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				this.add(board[i][j] = new Cell(), i, j);
		}

	}

	public static boolean isWon(Cell[][] board) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board[i][j].getToken() == 2)
					return false;
			}
		}

		return true;
	}

	
}
