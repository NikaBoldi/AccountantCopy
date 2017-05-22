package accountant.constants;

public enum State {

	ACTIVE("Active"),
	DELETED("Deleted");

	private String state;

	State(String state){
		this.state = state;
	}

	@Override
	public String toString(){
		return this.state;
	}

	public String getName(){
		return this.name();
	}

}
