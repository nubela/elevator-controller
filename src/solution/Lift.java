package solution;

public class Lift {

	Status status;
	Double position;
	int elevatorId;

	public Lift(int elevatorId) {
		this.elevatorId = elevatorId;
	}

	public int getElevatorId() {
		return elevatorId;
	}

	public Double getPosition() {
		return position;
	}

	public Status getStatus() {
		return status;
	}

	public void setElevatorId(int elevatorId) {
		this.elevatorId = elevatorId;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static enum Status {
		DOOR_OPEN, DOOR_CLOSE, IN_MOTION;
	}

}