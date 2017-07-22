package statemachine;

import java.util.ArrayList;

public class State {
	private ArrayList<Transition> outgoing;
	private StateData stateData;
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private ArrayList<Transition> incoming;

	public ArrayList<Transition> getIncoming() {
		return incoming;
	}

	public void setIncoming(ArrayList<Transition> incoming) {
		this.incoming = incoming;
	}

	public ArrayList<Transition> getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(ArrayList<Transition> outgoing) {
		this.outgoing = outgoing;
	}

	public StateData getStateData() {
		return stateData;
	}

	public void setStateData(StateData stateData) {
		this.stateData = stateData;
	}

	public State() {
		this.incoming = new ArrayList<Transition>();
		this.outgoing = new ArrayList<Transition>();
		this.stateData = new StateData();
	}

	public State(Transition incoming, Transition outgoing, StateData stateData) {
		this.incoming = new ArrayList<Transition>();

		for (Transition incomingTransition : this.incoming) {
			this.incoming.add(incomingTransition);
		}

		this.outgoing = new ArrayList<Transition>();

		for (Transition outgoingTransition : this.outgoing) {
			this.outgoing.add(outgoingTransition);
		}

		this.stateData = stateData;
	}

	public void addOutgoingTransition(Transition transition) {
		this.outgoing.add(transition);
	}

	public void addIncomingTransition(Transition transition) {
		this.incoming.add(transition);
	}
}
