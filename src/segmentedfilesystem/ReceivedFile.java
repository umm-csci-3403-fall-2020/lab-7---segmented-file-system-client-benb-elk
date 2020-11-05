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
		System.out.println("last packet number: " + dp.getPacketNumber());
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
	//String path = "src/";
	//path = path.concat(this.header.getFileName());
	String filePath = this.header.getFileName();
	// making sure to get rid of any extra spaces that might be hiding
	filePath = filePath.trim();
	System.out.println(filePath);
        try {
            File file = new File(filePath);
	    file.createNewFile();
            FileOutputStream out = null;
	    
            out = new FileOutputStream(file);

            Iterator it = this.dataPackets.keySet().iterator();
            while (it.hasNext()) {
                DataPacket dp = dataPackets.get(it.next());
		for(int i = 0; i < dp.getData().length; i++){
		    out.write(dp.getData()[i]);
		    out.flush();
		}
               // out.write(dp.getData());
            }
            out.close();
        } catch (IOException ioe) {
            System.out.println("File construction has failed with error message " + ioe + ".");
        } 
    }
}
