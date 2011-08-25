package solution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Lift {

	List<Integer> upwardsQueue = new LinkedList<Integer>();
	List<Integer> downwardsQueue = new LinkedList<Integer>();
	int direction = 0; // 0 if stationary, 1 if up, -1 if down.
	Double position = 0.0;
	Double positionDirty = 0.0;
	int elevatorId;
	int totalLevels;
	ControlManager controlManager;

	final Double POSN_TOLERANCE = 0.1;

	public Lift(ControlManager controlManager, int elevatorId, int totalLevels) {
		this.controlManager = controlManager;
		this.elevatorId = elevatorId;
		this.totalLevels = totalLevels;
	}

	/**
	 * Add a level to stop at as the lift goes towards in a direction. -1 down,
	 * 1 up.
	 * 
	 * @param direction
	 * @param level
	 */
	public void addLevelStop(int direction, int level) {
		if (direction == 1) {
			upwardsQueue.add(new Integer(level));
			Collections.sort(upwardsQueue);
		} else {
			downwardsQueue.add(new Integer(level));
			Collections.sort(downwardsQueue);
			Collections.reverse(downwardsQueue);
		}
		updated();
	}

	public void buttonPress(Integer level) {
		if (level == 32000) {

		}

		if (direction * (level - position.intValue()) > 0) {
			addLevelStop(direction, level);
		} else if (direction * (level - position.intValue()) < 0) {
			addLevelStop(direction * -1, level);
		} else {
			if (level - position.intValue() > 0) {
				addLevelStop(1, level);
			} else if (level - position.intValue() < 0) {
				addLevelStop(-11, level);
			} else if ((direction == 0) && (level == position.intValue())) {
				openCloseDoor();
			}
		}

	}

	public ControlManager getControlManager() {
		return controlManager;
	}

	public int getDirection() {
		return direction;
	}

	public List<Integer> getDirectionalQueue() {
		if (direction == 1)
			return getUpwardsQueue();
		else if (direction == -1)
			return getDownwardsQueue();
		else
			throw new RuntimeException(
					"Trying to get directional queue when lift is not moving!");
	}

	public List<Integer> getDownwardsQueue() {
		return downwardsQueue;
	}

	public int getElevatorId() {
		return elevatorId;
	}

	public Double getPosition() {
		return position;
	}

	public Double getPositionDirty() {
		return positionDirty;
	}

	public Double getPOSN_TOLERANCE() {
		return POSN_TOLERANCE;
	}

	public int getTotalLevels() {
		return totalLevels;
	}

	public List<Integer> getUpwardsQueue() {
		return upwardsQueue;
	}

	public Boolean isLevelInDirection(Integer level) {
		return (direction * (level - position.intValue()) > 0);
	}

	/**
	 * Open and closes a door.
	 */
	private void openCloseDoor() {
		// open door
		controlManager.getInstructionManager().openDoor(this, 1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// close door
		controlManager.getInstructionManager().openDoor(this, -1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		updated();
	}

	public void setControlManager(ControlManager controlManager) {
		this.controlManager = controlManager;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setDownwardsQueue(List<Integer> downwardsQueue) {
		this.downwardsQueue = downwardsQueue;
	}

	public void setElevatorId(int elevatorId) {
		this.elevatorId = elevatorId;
	}

	public void setPosition(Double position) {
		this.position = position;
		updated();
	}

	public void setPositionDirty(Double positionDirty) {
		this.positionDirty = positionDirty;
	}

	public void setTotalLevels(int totalLevels) {
		this.totalLevels = totalLevels;
	}

	public void setUpwardsQueue(List<Integer> upwardsQueue) {
		this.upwardsQueue = upwardsQueue;
	}

	/**
	 * Procs everytime a statistic about this lift is updated.
	 */
	public void updated() {
		updateFloorIndicator();
		updateStoppage();
		updateMovement();

		// remove dirty.
		positionDirty = position;
	}

	private void updateFloorIndicator() {

		if (direction == 0) {
			controlManager.getInstructionManager().setLiftFloorIndicator(this,
					position.intValue());
		}

		if (positionDirty.intValue() - position.intValue() != 0) {
			controlManager.getInstructionManager().setLiftFloorIndicator(this,
					position.intValue());
		}

		if (direction == 1) {
			controlManager.getInstructionManager().setLiftFloorIndicator(this,
					new Double(Math.ceil(position)).intValue());
		} else if (direction == -1) {
			controlManager.getInstructionManager().setLiftFloorIndicator(this,
					new Double(Math.floor(position)).intValue());
		}

	}

	private void updateMovement() {
		if (((upwardsQueue.size() > 0) || (downwardsQueue.size() > 0))
				&& (direction == 0)) {
			if (upwardsQueue.size() > 0) {
				direction = 1;
			} else if (downwardsQueue.size() > 0) {
				System.out.println("works");
				direction = -1;
			}
			controlManager.getInstructionManager()
					.moveElevator(this, direction);
		}
	}

	private void updateStoppage() {
		if (direction != 0) {
			Double difference;
			if (direction == 1) {
				difference = position - (new Double(upwardsQueue.get(0)));
				System.out.println(difference.toString());

				if (((POSN_TOLERANCE * -1) <= difference)
						&& (difference <= POSN_TOLERANCE)) {
					System.out.println("UP TURN!");
					direction = 0;
					upwardsQueue.remove(0);
				}

			} else if (direction == -1) {
				difference = position - (new Double(downwardsQueue.get(0)));

				if (((POSN_TOLERANCE * -1) <= difference)
						&& (difference <= POSN_TOLERANCE)) {
					System.out.println("DOWN TURN!");
					direction = 0;
					downwardsQueue.remove(0);
				}
			}

			if (direction == 0) {
				// stop
				controlManager.getInstructionManager().moveElevator(this,
						direction);
				openCloseDoor();
			}

		}
	}
}