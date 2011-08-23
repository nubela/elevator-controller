package solution;

public class Instruction {

	ControlManager controlManager;

	public Instruction(ControlManager controlManager) {
		this.controlManager = controlManager;
	}

	/**
	 * Get all Lift's velocity.
	 * 
	 * @return Translated String
	 */
	public void getAllLiftsVelocity() {
		controlManager.getTcpConnector().write("v");
	}

	/**
	 * Inspects lift's position.
	 * 
	 * @param lift
	 * @return Translated String
	 */
	public void inspectPositionOfLift(Lift lift) {
		controlManager.getTcpConnector().write("w " + lift.getElevatorId());
	}

	/**
	 * Moves an elevator towards a certain direction, or stops it. -1,
	 * Downwards; 0, Stops; 1. Upwards.
	 * 
	 * @param lift
	 * @param direction
	 * @return Translated String
	 */
	public void moveElevator(Lift lift, int direction) {
		controlManager.getTcpConnector().write(
				"m " + lift.getElevatorId() + " " + direction);
	}

	/**
	 * Opens or closes a door given the lift. 1 opens it, -1 closes.
	 * 
	 * @param lift
	 * @param open
	 * @return Translated String
	 */
	public void openDoor(Lift lift, int open) {
		controlManager.getTcpConnector().write(
				"d " + lift.getElevatorId() + " " + open);
	}

	/**
	 * Sets floor indicator of a lift.
	 * 
	 * @param lift
	 * @param floorNumber
	 * @return Translated String
	 */
	public void setLiftFloorIndicator(Lift lift, int floorNumber) {
		controlManager.getTcpConnector().write(
				"s " + lift.getElevatorId() + " " + floorNumber);
	}

}
