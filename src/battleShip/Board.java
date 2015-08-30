package battleShip;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/** Klasa koja prestavlja plocu igrice battleship */
public class Board extends GridPane {

	// matrica sa cell objektima koja prestaclja plocu za igru
	Cell[][] board = new Cell[10][10];

	/** Metoda koja postavlja protivnicke brodove */
	public void placeEnemyShips() {

		// prvo postavljamo brod duzine 5, zatim smanjujemo duzinu za jedan i
		// postavljamo brod duzine 4 i sve tako dok je enemyShipLength > 0
		//
		int enemyShipLength = 5;

		do {

			// varijabla koja odredjuje da li ce se brod postaviti horizontalno
			// ili vertikalno
			int horizontal = (int) (Math.random() * 2);
			// ako je horizontal == 1 brod se postavlja horizontalno
			// ako je horizontak == 0 brod se postavlja vertikalno

			if (horizontal == 1) {

				int i, j;

				boolean isFree = false;
				do {
					isFree = true;

					// generisanje koordinata polja od kojeg ce se postavljati
					// brod
					i = (int) (Math.random() * 10);
					j = (int) (Math.random() * (10 - enemyShipLength));

					// provjeravamo da li su potrebna polja slobodna
					for (int k = j; k < j + enemyShipLength; k++) {
						if (board[i][k].token != 1) {
							isFree = false;
						}
					}

					// generisemo koordinate i i j sve dok brod duzine
					// enemyShipLength ne moze stati u odabrana polja
					// pocevsi od kliknutog polja
				} while (!isFree);

				// ukoliko su predhodni uslovi ispunjeni postavljamo brod
				for (int k = j; k < j + enemyShipLength; k++) {
					board[i][k].token = 2;
				}

				// horizontalno postavljanje broda
			} else if (horizontal == 0) {

				// koordinate u koje postavljamo brod
				int i, j;

				boolean isFree = false;

				// generisemo nove koordinate i i j sve dok brod duzine
				// enemyShipLength ne moze stati vertikalno pocevsi od kliknutog
				// polja
				do {
					isFree = true;
					i = (int) (Math.random() * (10 - enemyShipLength));
					j = (int) (Math.random() * 10);

					for (int k = i; k < i + enemyShipLength; k++) {
						if (board[k][j].token != 1) {
							isFree = false;
						}
					}
				} while (!isFree);

				// ukoliko je predhodni uslov ispunjen postaljmo brod
				for (int k = i; k < i + enemyShipLength; k++) {
					board[k][j].token = 2;
				}
			}

			// umanjujemo duzinu broda i provjeravamo da li se postavljanje
			// brodova nastavlja
			enemyShipLength--;
		} while (enemyShipLength > 0);

	}

	/** Konstruktor */
	Board() {
		// konstruktor pravi matricu polja
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				this.add(board[i][j] = new Cell(i, j), i, j);
		}

	}

	/**
	 * Metoda koja provjerava da li je trenutni igrac potopio sve protivnicke
	 * brodove
	 */
	public static boolean isWon(Cell[][] board) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board[i][j].getToken() == 2)
					return false;
			}
		}

		return true;
	}
	
	/** Metoda koja resetuje igru */
	public static void resetGame(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				BattleShip.boardUser.board[i][j].setToken(1);
				BattleShip.boardUser.board[i][j].ellipse.setFill(Color.LIGHTGRAY);
				
				BattleShip.boardComp.board[i][j].setToken(1);
				BattleShip.boardComp.board[i][j].ellipse.setFill(Color.LIGHTGRAY);
				
				BattleShip.gameStarted = false;
				
				BattleShip.userShipLength = 5;
			}
		}
	}

}
