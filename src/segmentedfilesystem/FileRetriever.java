package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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

		byte[] sendBuf = new byte[1028];
		InetAddress address = InetAddress.getByName(server);

		DatagramSocket socket = new DatagramSocket();
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);

		socket.send(sendPacket);
		// Need to implement PacketManager class
		PacketManager pm = new PacketManager();
		System.out.println("receiving packets");
		while (!pm.allPacketsReceived()) {
			byte[] buf = new byte[1028];
			DatagramPacket receivedPacket = new DatagramPacket(buf, buf.length);
			socket.receive(receivedPacket);

			pm.intake(receivedPacket);
		}
		System.out.println("packets received");
		socket.close();
		pm.saveFiles();
	}
}
