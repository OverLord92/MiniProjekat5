package battleShip;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * objekat cell moze imati 4 stanja: - this.token == 1 - na polju nema broda -
 * this.token == 2 - na polju se nalazi ispravan brod - this.token == 3 - na
 * polju se nalazi pogodjen brod - this.token == 4 - polje koje je protivnik
 * gadjao a na kojem nije bilo broda
 */

public class Cell extends Pane {

	int token;

	// i i j su koordinate polja unutar GridPane-a board
	int i;
	int j;

	// ellipse objekat pomocu kojeg vizuelno predstavljamo trenutno stanje polja
	Ellipse ellipse;

	/** geter metoda za token */
	public int getToken() {
		return token;
	}
	
	/** seter metoda za token koja se koristi za resetovanje igre*/
	public void setToken(int newToken){
		this.token = newToken;
	}
	

	/** Konstruktor kojem prosljedjujemo koordinate polja unutar grida */
	public Cell(int i, int j) {
		this.i = i;
		this.j = j;
		// pocetno stanje je this.token == 1
		token = 1;
		setStyle("-fx-border-color: black");
		this.ellipse = new Ellipse(30, 30, 20, 20);

		// u slucaju resize prozora, sadrzaj se prilagodjava novim dimenzijama
		this.ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		this.ellipse.centerYProperty().bind(this.heightProperty().divide(2));
		this.ellipse.radiusXProperty().bind(
				this.widthProperty().divide(2).subtract(10));
		this.ellipse.radiusYProperty().bind(
				this.heightProperty().divide(2).subtract(10));

		this.ellipse.setFill(Color.LIGHTGRAY);
		this.setPrefSize(800, 800);
		getChildren().add(ellipse);

		// hendlovanje klika
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			// Override the handle method
			public void handle(MouseEvent e) {

				// ukoliko igra nije jos pocela klikovima se postavljaju brodovi
				if (!BattleShip.gameStarted)
					handleMouseClickPreGame(e);
				else {
					// nakon pocetka igre klikom se gadjaju brodovi protivnika
					handleMouseClickDuringGame();
				}
			}
		});

	}

	/**
	 * Metoda koja poostavlja brod, tj prebacuje sadrzaj tokena na this.token ==
	 * 2, i vizuelno predstavlja postavljanje broda koristeci crni krug
	 */
	public void placeShip() {
		ellipse.setFill(Color.BLACK);
		token = 2;
	}

	/**
	 * Metoda koja potapa brod, tj prebacuje sadrzaj tokena na this.token == 3,
	 * i vizuelno predstavlja potapanje broda koristeci crveni krug
	 */
	public void destroyShip() {
		ellipse.setFill(Color.RED);
		token = 3;
	}

	/**
	 * Metoda koja masi brod, tj prebacuje sadrzaj tokena na this.token == 4, i
	 * vizuelno predstavlja masenje broda koristeci plavi krug
	 */
	public void failShip() {
		ellipse.setFill(Color.BLUE);
		token = 4;
	}

	/**
	 * Metoda koja se poziva u slucaju klika nako pritiska korisnika na "start"
	 * button
	 */
	private void handleMouseClickDuringGame() {
		// ako je polje na koje je korisnik kliknuo prazno - promasaj
		if (token == 1)
			failShip();
		// ako se ne polju na koje je igrac kliknuo nalazi protivnici brodic -
		// pogodak
		else if (token == 2)
			destroyShip();

		// nakon odigranog poteza igraca provjeravamo da li je igrac pobjednik
		if (Board.isWon(BattleShip.boardComp.board)) {
			// ukoliko jeste postaljvljamo tekst u label lblStatus
			BattleShip.lblStatus.setText("Korisnik je pobjedio");
		}

		// kompjuterov potez
		enemysShot();

		// provjeravamo da li je kompjuter povjednik
		if (Board.isWon(BattleShip.boardUser.board)) {
			BattleShip.lblStatus.setText("Kompjuter je pobjedio");
		}

		// ako ni igrac ni kompjuter nisu pobjednici igra se nastavlja

	}

	/** Metoda koja generise potez kompjutera */
	public static void enemysShot() {

		int i, j;

		do {
			i = (int) (Math.random() * 10);
			j = (int) (Math.random() * 10);
			// koordinate i i j se generisu sve dok polje[i][j] ne sadrzi token
			// 3(kompjuter je vec gadjao to polje i pogodio) i 4 (kompjuter je
			// vec gadjao to polje i pormasio)
		} while (BattleShip.boardUser.board[i][j].getToken() == 3
				|| BattleShip.boardUser.board[i][j].getToken() == 4);

		// ako polje sadrzi token 1 kompjuter je promasio, a ako polje sadrzi
		// token 2 kompjuter je pogodio
		if (BattleShip.boardUser.board[i][j].getToken() == 1)
			BattleShip.boardUser.board[i][j].failShip();
		else if (BattleShip.boardUser.board[i][j].getToken() == 2)
			BattleShip.boardUser.board[i][j].destroyShip();
	}

	/**
	 * Metoda koja se poziva u slucaju klika, prije klika korisnika na button
	 * "start", tj sluzi za postavljanje korisnickih brodica u pregame fazi
	 */
	private void handleMouseClickPreGame(MouseEvent e) {

		// metoda posatvlja brodove duzine 5, 4, 3, 2, 1 respektivno
		// koristimo static varijablu klase Battleship koju dodjeljujemo
		// varijabli shipLength
		int shipLength = BattleShip.userShipLength;

		// prov provjeravamo da li smo vec postavili isve brodice
		if (shipLength > 0) {
			// zatim provjeravamo da li je korisnik kliknuo lijevi ili desni
			// taster

			// ukoliko je korisnik kliknuo lijevi taster prod postavljamo
			// vertikalno
			if (e.getButton() == MouseButton.PRIMARY) {

				// varijablama i i j dodjeljujemo koordinate kliknutog polja
				int i = this.i;
				int j = this.j;

				boolean isFree = true;

				// provjeravamo za odabrano polje da li brod duzine shipLength
				// moze stati vertikalno
				// pocevsi od odabranog polja na dole
				for (int k = j; k < j + shipLength; k++) {
					if (j + shipLength > 10) {
						isFree = false;
					} else if (BattleShip.boardUser.board[i][k].token != 1) {
						isFree = false;
					}
				}

				// ukoliko brod moze vertikalno stati u matricu i ako su polja
				// slobodna postavljamo brod
				if (isFree) {
					for (int k = j; k < j + shipLength; k++) {
						BattleShip.boardUser.board[i][k].placeShip();
						BattleShip.boardUser.board[i][k].token = 2;
					}
					BattleShip.userShipLength--;
				}
			}

			// ukoliko je korisnik kliknuo desnim klikom brod postavljamo
			// horizontalno
			else if (e.getButton() == MouseButton.SECONDARY) {

				// varijablama i i j dodjeljujemo koordinate kliknutog polja
				int i = this.i;
				int j = this.j;

				boolean isFree = true;

				// provjeravamo za odabrano polje da li brod duzine shipLength
				// moze stati horizontalno pocevsi od kliknutog polja
				for (int k = i; k < i + shipLength; k++) {
					if (j + shipLength > 10) {
						isFree = false;
					} else if (BattleShip.boardUser.board[k][j].token != 1) {
						isFree = false;
					}
				}

				// ako brod moze stati i ako su potrebna polja slobodna
				// postavljamo brod
				if (isFree) {
					for (int k = i; k < i + shipLength; k++) {
						BattleShip.boardUser.board[k][j].placeShip();
						BattleShip.boardUser.board[k][j].token = 2;
					}
					BattleShip.userShipLength--;
				}
			}
		}
		
		// nako postaljvljenog broda duzine 5 umanjujemo shipLength za 1. Zatim
		// postavljamo brod duzine 4 itd sve dok ne dodjemo do 0


	}
}