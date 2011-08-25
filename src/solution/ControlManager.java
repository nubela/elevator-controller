package solution;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ControlManager {

	UpdateManager updateManager;
	TCPConnector tcpConnector;
	List<Lift> lifts = new LinkedList<Lift>();
	InstructionManager instructionManager;
	int totalLevels;

	public ControlManager(String host, int port, int noOfLifts, int noOfLevels) {
		updateManager = new UpdateManager(this);
		tcpConnector = new TCPConnector(host, port, updateManager);
		totalLevels = noOfLevels;
		for (int i = 0; i < noOfLifts; i++) {
			lifts.add(new Lift(this, i + 1, noOfLevels));
		}
		instructionManager = new InstructionManager(this);
	}

	public InstructionManager getInstructionManager() {
		return instructionManager;
	}

	public Lift getLiftById(int id) {
		for (Lift l : lifts) {
			if (l.getElevatorId() == id)
				return l;
		}
		throw new RuntimeException("Can't find Lift with ID: "
				+ (new Integer(id)).toString());
	}

	public List<Lift> getLifts() {
		return lifts;
	}

	public TCPConnector getTcpConnector() {
		return tcpConnector;
	}

	public int getTotalLevels() {
		return totalLevels;
	}

	public UpdateManager getUpdateManager() {
		return updateManager;
	}

	/**
	 * Manages requests and coordinates lifts to take up the role.
	 * 
	 * @param level
	 * @param direction
	 */
	public void levelRequest(Integer level, Integer direction) {
		Map<Lift, Integer> difference = new HashMap<Lift, Integer>();

		for (Lift l : lifts) {
			if (l.getDirection() == 0) {
				difference.put(l, Math.abs(level - l.getPosition().intValue()));
			} else if (!l.isLevelInDirection(level)) {
				difference.put(l, Math.abs(level - l.getPosition().intValue())
						+ totalLevels);
			}
		}

		Integer smallest = 32000;
		for (Integer i : difference.values()) {
			if (i < smallest) {
				smallest = i;
			}
		}

		for (Entry<Lift, Integer> e : difference.entrySet()) {
			if (e.getValue() == smallest) {
				e.getKey().buttonPress(level);
				return;
			}
		}

	}

	public void setInstructionManager(InstructionManager instructionManager) {
		this.instructionManager = instructionManager;
	}

	public void setLifts(List<Lift> lifts) {
		this.lifts = lifts;
	}

	public void setTcpConnector(TCPConnector tcpConnector) {
		this.tcpConnector = tcpConnector;
	}

	public void setTotalLevels(int totalLevels) {
		this.totalLevels = totalLevels;
	}

	public void setUpdateManager(UpdateManager updateManager) {
		this.updateManager = updateManager;
	}

	public static void main(String[] args) {
		ControlManager controlManager = new ControlManager("localhost", 4711,
				2, 5);
	}

}
