package statemachine;

public class StateData {
	private Object data;

	public StateData() {
		this.data = new Object();
	}

	public StateData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "StateData [data=" + data + "]";
	}

}
