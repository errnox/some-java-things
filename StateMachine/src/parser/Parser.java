package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import statemachine.State;
import statemachine.StateData;
import statemachine.StateMachine;
import statemachine.Transition;

public class Parser {
	private String INPUT_FILE_NAME;
	private ArrayList<String> successors;

	private Map<String, ArrayList<String>> states = new HashMap<String, ArrayList<String>>();

	public Map<String, ArrayList<String>> getStates() {
		return states;
	}

	public Parser() {
		this.INPUT_FILE_NAME = "input.txt";
	}

	public Parser(String fileName) {
		this.INPUT_FILE_NAME = fileName;
	}

	public void parse() {
		try {
			FileReader fileReader = new FileReader(this.INPUT_FILE_NAME);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = "";
			String[] lineTokens;

			String currentState = "";
			ArrayList<String> successors = new ArrayList<String>();

			while ((line = bufferedReader.readLine()) != null) {
				if (!line.matches("^ *->.*$") && !line.matches("^ *}.*$")) {
					currentState = line.replaceAll("\\{ *$", "").trim();
				} else if (line.matches("^ *->.*$")) {
					successors.add(line);
				} else if (line.matches("^ *\\} *$")) {
					this.states.put(currentState, successors);
					successors = new ArrayList<String>();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void log() {
		String key;
		ArrayList<String> value;

		for (Entry<String, ArrayList<String>> entry : this.states.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			System.out.println(key);

			for (String v : value) {
				System.out.println(v);
			}
		}
	}

	// TODO
	public StateMachine buildStateMachine() {
		StateMachine stateMachine = new StateMachine();
		String key;
		ArrayList<String> value;
		State state;
		Transition transition;

		for (Entry<String, ArrayList<String>> entry : this.states.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			state = new State();
			state.setDescription(key);
			State targetState;

			for (String v : value) {
				transition = new Transition();

				StateData stateData = new StateData(value);

				if (stateMachine.containsStateWithStateData(stateData)) {
					targetState = stateMachine.getStateWithStateData(stateData);
				} else {
					targetState = new State();
					targetState.setStateData(new StateData(value));
					stateMachine.addState(targetState);
				}

				transition.setTargetState(targetState);
				stateMachine.addState(state);
				System.out.println(state.getDescription());
			}
		}

		return stateMachine;
	}

	public static void main(String[] args) {
		Parser parser = new Parser("res/stateMachineDescription");
		parser.parse();
		// parser.log();
		StateMachine stateMachine = parser.buildStateMachine();
		System.out.println(stateMachine.getStates().size());
	}
}
