package solution;

/**
 * @author nubela
 */
public class TCPReader extends Thread {

	TCPConnector tcpConnector;

	public TCPReader(TCPConnector tcpConnector) {
		this.tcpConnector = tcpConnector;
	}

	@Override
	public void run() {

		while (true)
			tcpConnector.read();

	}

}
