package statemachine;

import java.util.ArrayList;

public class StateMachine {
	private State startState;
	private ArrayList<State> states;

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	public State getStartState() {
		return startState;
	}

	public void setStartState(State startState) {
		this.startState = startState;
	}

	public State getEndState() {
		return endState;
	}

	public void setEndState(State endState) {
		this.endState = endState;
	}

	private State endState;

	public StateMachine() {
		this.startState = new State();
		this.endState = new State();
		this.states = new ArrayList<State>();
	}

	public void addState(State state) {
		states.add(state);
	}

	public void addStates(State[] states) {
		for (State state : states) {
			this.states.add(state);
		}
	}

	public boolean containsStateWithStateData(StateData stateData) {
		boolean doesContain = false;

		for (State state : this.states) {
			if (state.getStateData() == stateData) {
				doesContain = true;
			}
		}

		return doesContain;
	}

	public State getStateWithStateData(StateData stateData) {
		State wantedState = null;

		for (State state : this.states) {
			if (state.getStateData() == stateData) {
				wantedState = state;
			}
		}

		return wantedState;
	}
}
