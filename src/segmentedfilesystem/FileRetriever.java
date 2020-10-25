package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class FileRetriever {
	String server;
	int port;

	public FileRetriever(String server, int port) {
        // Save the server and port for use in `downloadFiles()`
        //...
		this.server = server;
		this.port = port;
	}

	public void downloadFiles() throws IOException {
        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.

		byte[] buf = new byte[1028];

		DatagramSocket socket = new DatagramSocket(port);
		DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, port);

		socket.send(sendPacket);
		// Need to implement PacketManager class
		PacketManager pm = new PacketManager();

		while (!pm.allPacketsReceived()) {
			DatagramPacket receivedPacket = new DatagramPacket(buf, buf.length);
			socket.receive(receivedPacket);

			pm.intake(receivedPacket);
		}
	}
}
