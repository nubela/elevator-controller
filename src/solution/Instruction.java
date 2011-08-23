package solution;

public class Instruction {

	/**
	 * Get all Lift's velocity.
	 * 
	 * @return Translated String
	 */
	public String getAllLiftsVelocity() {
		return "v";
	}

	/**
	 * Inspects lift's position.
	 * 
	 * @param lift
	 * @return Translated String
	 */
	public String inspectPositionOfLift(Lift lift) {
		return "w " + lift.getElevatorId();
	}

	/**
	 * Moves an elevator towards a certain direction, or stops it. -1,
	 * Downwards; 0, Stops; 1. Upwards.
	 * 
	 * @param lift
	 * @param direction
	 * @return Translated String
	 */
	public String moveElevator(Lift lift, int direction) {
		return "m " + lift.getElevatorId() + " " + direction;
	}

	/**
	 * Opens or closes a door given the lift. 1 opens it, -1 closes.
	 * 
	 * @param lift
	 * @param open
	 * @return Translated String
	 */
	public String openDoor(Lift lift, int open) {
		return "d " + lift.getElevatorId() + " " + open;
	}

	/**
	 * Sets floor indicator of a lift.
	 * 
	 * @param lift
	 * @param floorNumber
	 * @return Translated String
	 */
	public String setLiftFloorIndicator(Lift lift, int floorNumber) {
		return "s " + lift.getElevatorId() + " " + floorNumber;
	}

}
