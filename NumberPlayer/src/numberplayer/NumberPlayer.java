package numberplayer;

public class NumberPlayer {
	public NumberPlayer() {

	}

	public static void main(String[] args) {
		// Run another thread in parallel
		// CustomThread customThread = new CustomThread("92929292929292929");
		// customThread.parse();

		// String input =
		// "0000000111111000001111111100000002222222200000004444444222222222000000000999999999000008888888232323230000022222"
		// +
		// "00000000444444444400000626262626200006622662266220000099229922929292999222000000";
		String input = "32829284293048381010743299999999";
		Parser parser = new Parser(input);
		parser.parse();

	}
}
