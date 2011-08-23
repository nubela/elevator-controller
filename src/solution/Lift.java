package solution;

public class Lift {

	Status status;
	Status position;
	int elevatorId;

	public Lift(int elevatorId) {
		this.elevatorId = elevatorId;
	}

	public int getElevatorId() {
		return elevatorId;
	}

	public Status getPosition() {
		return position;
	}

	public Status getStatus() {
		return status;
	}

	public void setElevatorId(int elevatorId) {
		this.elevatorId = elevatorId;
	}

	public void setPosition(Status position) {
		this.position = position;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static enum Status {
		DOOR_OPEN, DOOR_CLOSE, IN_MOTION;
	}

}