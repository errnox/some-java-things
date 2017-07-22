package numberplayer;

public class CustomThread extends Thread {
	private Parser parser;
	String input;
	
	public CustomThread() {
		
	}

	public CustomThread(String input) {
		this.input = input;
		parser = new Parser(input);
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread");
		}
	}
	
	public void parse() {
		for (int i = 0; i < 10; i++) {
			try {
				sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String input = "2838292929299999999";
			parser.parse();
		}
	}
}
