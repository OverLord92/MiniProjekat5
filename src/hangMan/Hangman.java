package hangMan;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {

	// array u koji smjestamo pogotke
	public static char[] correctGuess;
	// lista u koju smjetamo promasaje
	public static ArrayList<Character> wrongGuess = new ArrayList<>();
	// rijec koju korisnik treba pogoditi, definisana kao String na nivou klase
	// kako bi mu sepomoglo pristupiti iz svih metoda
	public static String word;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// broj dozvoljenih gresaka
		int numberOfGuesses = 5;

		// vadimo proizvoljnu rijec iz niza Words.words
		word = Words.words[(int) (Math.random() * Words.words.length)];

		// niz u koji smjetamo pogodtke punimo sa karakterima '_' kako bi mogli
		// printati trenbutno stanje
		correctGuess = new char[word.length()];
		for (int i = 0; i < correctGuess.length; i++)
			correctGuess[i] = '_';

		System.out.println("Igrate igricu hangman.\nPogadjate java keyword.\n");

		// igra traje sve dok korisnik ne pobjedi ili ne potrosi sve pokusaje
		do {
			progress(); // printanje trenutnog stanja
			
			// korisnik unosi slovo
			System.out.print("Unesite slovo: ");
			char charGuess = input.next().charAt(0);

			// prvo porvjeracamo da li je korisnik vec pokusao unjeti to slovo
			if (alreadyPlaced(charGuess)) {
				System.out.println("\nVec ste pokusali to slovo.\n");
				// ako nije da li se to slovo nalazi u rijeci koju treba pogodit
			} else if (word.contains(charGuess + "")) {
				System.out.println("Pogodiliste.\n");
				// ako je korisnik pogodio, slovo ubacujemo u pogodtke
				place(charGuess);
			} else {
				// ako je korisnik promasio umanjujemo broj pokusaja
				numberOfGuesses--;
				// ukoliko je broj pokusaja veci od nula printamo broj preostalih pokusaja
				if (numberOfGuesses > 0) {
					System.out.println("Pogrijesili ste. Mozete napraviti jos "
							+ numberOfGuesses + " greske.\n");
					wrongGuess.add(charGuess);
				} else
					// ukoliko je broj pokusaja jednak 0 korisnik je izgubio
					System.out.println("Izgubili ste");
			}

		} while (!isWon() && numberOfGuesses > 0);

		// pirntanje poruke korisniku ukoliko je on pobjedio
		if (isWon()) {
			System.out.println("Pobjedili ste. Rijec je: " + word);
		}

		input.close();
	}

	/** Metoda koja printa trenutno stanje pogodaka */
	public static void progress() {
		System.out.print("Trenutno stanje: ");
		for (int i = 0; i < correctGuess.length; i++) {
			System.out.print(correctGuess[i] + " ");
		}

		System.out.println("\n");
	}

	/** Metoda koj aprovjerava da li je korisnik pobjedio */
	public static boolean isWon() {
		for (int i = 0; i < correctGuess.length; i++) {
			if (correctGuess[i] == '_')
				return false;
		}

		return true;
	}

	/** Metoda koja ubacuje slovo c u pogodke */
	public static void place(char c) {
		int i;
		for (i = 0; i < word.length(); i++)
			if (c == word.charAt(i))
				correctGuess[i] = c;

	}

	/** Provjeravamo da li je korisnik vec pokusao ubaciti slovo c */
	public static boolean alreadyPlaced(char c) {

		// prolazimo krot listu u koju smjestmo greske
		for (int i = 0; i < wrongGuess.size(); i++) {
			if (c == wrongGuess.get(i))
				return true;
		}

		// zatim kroz array sa pogodcima
		for (int i = 0; i < correctGuess.length; i++) {
			if (c == correctGuess[i])
				return true;
		}

		return false;
	}

}
