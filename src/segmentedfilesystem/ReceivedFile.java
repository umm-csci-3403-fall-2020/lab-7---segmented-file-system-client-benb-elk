package segmentedfilesystem;

import java.io.*;
import java.util.*;

public class ReceivedFile {
    byte fileID;
    TreeMap<Integer, DataPacket> dataPackets;
    HeaderPacket header;
    int totalDataPackets;

    public ReceivedFile(byte fileID, Packet packet) {
        this.fileID = fileID;
        dataPackets = new TreeMap<Integer, DataPacket>();
        header = null;
        totalDataPackets = -1;

        this.addPacket(packet);
    }

    public void addPacket(Packet packet) {
        if (packet.isHeaderPacket() == true) {
            this.header = (HeaderPacket) packet;
            return;
        }

        DataPacket dp = (DataPacket) packet;
        if (dp.isLastPacket() == true) {
		System.out.println("isLastPacket logic has been hit");
            totalDataPackets = dp.getPacketNumber() + 1;
        }
        dataPackets.put(dp.getPacketNumber(), dp);
    }

    public byte getFileID() {
        return this.fileID;
    }

    public boolean isCompleted() {
        if (this.header == null) {
            return false;
        } else if (this.totalDataPackets == -1) {
            return false;
        }
	System.out.println("size: " + this.dataPackets.size());
	System.out.println("total: " + this.totalDataPackets);
        return (this.dataPackets.size() == this.totalDataPackets);
    }

    public String returnFileName() {
       return  this.header.getFileName();
    }

    public void constructFile() {
	System.out.println(this.header.getFileName());
        File file = new File("/src/" + this.header.getFileName());
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);

            Iterator it = this.dataPackets.keySet().iterator();
            while (it.hasNext()) {
                DataPacket dp = dataPackets.get(it.next());
                out.write(dp.getData());
            }
            out.close();
        } catch (IOException ioe) {
            System.out.println("File construction has failed with error message " + ioe + ".");
        } 
    }
}
