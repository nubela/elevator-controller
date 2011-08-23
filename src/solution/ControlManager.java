package solution;

import java.util.LinkedList;
import java.util.List;

public class ControlManager {

	UpdateManager updateManager;
	TCPConnector tcpConnector;
	List<Lift> lifts = new LinkedList<Lift>();

	public ControlManager(String host, int port, int noOfLifts) {
		updateManager = new UpdateManager(this);
		tcpConnector = new TCPConnector(host, port, updateManager);
		for (int i = 0; i < noOfLifts; i++) {
			lifts.add(new Lift(i + 1));
		}
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

	public UpdateManager getUpdateManager() {
		return updateManager;
	}

	public void setLifts(List<Lift> lifts) {
		this.lifts = lifts;
	}

	public void setTcpConnector(TCPConnector tcpConnector) {
		this.tcpConnector = tcpConnector;
	}

	public void setUpdateManager(UpdateManager updateManager) {
		this.updateManager = updateManager;
	}

	public static void main(String[] args) {
		ControlManager controlManager = new ControlManager("localhost", 4711, 2);
		controlManager.getTcpConnector().write("m 1 1");
	}

}
