package statemachine;

public class Transition {
	private int ranking;

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public State getTargetState() {
		return targetState;
	}

	public void setTargetState(State targetState) {
		this.targetState = targetState;
	}

	private State targetState;

	public Transition() {
		this.ranking = 0;
		this.targetState = new State();
	}

	public Transition(State tarState, int ranking) {
		this.targetState = new State();
		this.ranking = ranking;
	}
}
