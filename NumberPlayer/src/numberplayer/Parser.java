package numberplayer;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import javax.xml.ws.Dispatch;

public class Parser {
	Player player;
	GraphicalAnalyzer graphicalAnalyzer;

	private static char currentChar;
	private static String input;
	private static CharacterIterator it;

	public final static char ZERO = '0';
	public final static char ONE = '1';
	public final static char TWO = '2';
	public final static char THREE = '3';
	public final static char FOUR = '4';
	public final static char FIVE = '5';
	public final static char SIX = '6';
	public final static char SEVEN = '7';
	public final static char EIGHT = '8';
	public final static char NINE = '9';

	public Parser(String input) {
		player = new Player();
		this.input = input;
		
		graphicalAnalyzer = new GraphicalAnalyzer();
	}

	public void parse() {
		it = new StringCharacterIterator(input);

		for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			currentChar = ch;
			dispatchCh(ch);
			
			graphicalAnalyzer.setCh(ch);
			graphicalAnalyzer.update(ch);
		}
	}

	public void dispatchCh(char ch) {
		player.play(ch);
	}
}
