package solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nubela Library to communicate with the Elevator UI.
 */
public class TCPConnector {

	public static void main(String[] args) {
		TCPConnector tcpConnector = new TCPConnector("localhost", 4711, null);
	}

	Socket socket;
	PrintWriter out;
	BufferedReader in;
	TCPListener listener;

	List<String> incoming = new LinkedList<String>();

	Lock lock = new ReentrantLock();

	public TCPConnector(String host, int port, TCPListener listener) {
		try {
			socket = new Socket("127.0.0.1", 4711);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection");
			System.exit(1);
		}
		this.listener = listener;

		// spawn reader thread
		new TCPReader(this).start();
		System.out.println("works");
	}

	public void read() {
		lock.lock();
		try {
			if (in.ready())
				incoming.add(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void write(String writeline) {
		lock.lock();
		try {
			out.write(writeline);
		} finally {
			lock.unlock();
		}
	}

}
