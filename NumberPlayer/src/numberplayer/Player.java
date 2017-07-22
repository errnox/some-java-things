package numberplayer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Player {
	private AudioStream audioStream;
	private InputStream inputStream;
	private String input;

	private final String ZERO_SOUND = "src/sounds/zero.wav";
	private final String ONE_SOUND = "src/sounds/one.wav";
	private final String TWO_SOUND = "src/sounds/two.wav";
	private final String THREE_SOUND = "src/sounds/three.wav";
	private final String FOUR_SOUND = "src/sounds/four.wav";
	private final String FIVE_SOUND = "src/sounds/five.wav";
	private final String SIX_SOUND = "src/sounds/six.wav";
	private final String SEVEN_SOUND = "src/sounds/seven.wav";
	private final String EIGHT_SOUND = "src/sounds/eight.wav";
	private final String NINE_SOUND = "src/sounds/nine.wav";

	public Player() {

	}

	private void configure() {
		try {
			inputStream = new FileInputStream(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			audioStream = new AudioStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setInput(String input) {
		this.input = input;
	}

	private void doPlay() {
		AudioPlayer.player.start(audioStream);
		try {
			// Play only a specific amount of milliseconds of the audio data
			AudioPlayer.player.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.stop(audioStream);
	}

	public void play(char ch) {
		switch (ch) {
		case Parser.ZERO:
			setInput(ZERO_SOUND);
			configure();
			doPlay();
			break;
		case Parser.ONE:
			setInput(ZERO_SOUND);
			configure();
			doPlay();
			break;
		case Parser.TWO:
			setInput(TWO_SOUND);
			configure();
			doPlay();
			break;
		case Parser.THREE:
			setInput(THREE_SOUND);
			configure();
			doPlay();
			break;
		case Parser.FOUR:
			setInput(FOUR_SOUND);
			configure();
			doPlay();
			break;
		case Parser.FIVE:
			setInput(FIVE_SOUND);
			configure();
			doPlay();
			break;
		case Parser.SIX:
			setInput(SIX_SOUND);
			configure();
			doPlay();
			break;
		case Parser.SEVEN:
			setInput(SEVEN_SOUND);
			configure();
			doPlay();
			break;
		case Parser.EIGHT:
			setInput(EIGHT_SOUND);
			configure();
			doPlay();
			break;
		case Parser.NINE:
			setInput(NINE_SOUND);
			configure();
			doPlay();
			break;
		default:
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Character \'" + ch
					+ "\' is not in supported char set (\'0\', \'1\', \'2\', \'3\', \'4\', \'5\', \'6\', \'7\', \'8\', \'9\').");
		}

	}
	// public static void main(String[] args) throws IOException {
	// for (int i = 0; i < 3; i++) {
	// Player player = new Player();
	// player.play('0');
	// }
	// }
}
