package solution;

import java.util.LinkedList;
import java.util.List;

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
		controlManager.getLiftById(1).addLevelStop(1, 5);
		controlManager.getLiftById(1).addLevelStop(-1, 0);
	}

}
