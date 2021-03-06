package segmentedfilesystem;

import java.io.IOException;
import java.util.*;
import java.net.DatagramPacket;

public class PacketManager {
    ReceivedFile[] files;

    public PacketManager() {
        files = new ReceivedFile[3];
    }

    public void intake(DatagramPacket receivedPacket) {
        byte[] data;
        data = receivedPacket.getData();
        Packet packet = constructPacket(data, receivedPacket.getLength());
        // know we'll only receive 3 files, hard code files list to 3
        // boolean foundFile = false;
        for (int i = 0; i < 3; i++) {
            if (files[i] == null) {
                ReceivedFile newFile = new ReceivedFile(packet.getFileID(), packet);
		files[i] = newFile;
                break;
            } else if (files[i].getFileID() == packet.getFileID()) {
                files[i].addPacket(packet);
                break;
            }
        }
    }

    public boolean allPacketsReceived() {
        int allComplete = 0;
        for (int i = 0; i < 3; i++) {
            if (files[i] == null) {
                return false;
            } else if (files[i].isCompleted()) {
                allComplete++;
            }
        }

        if (allComplete == 3) {
            return true;
        }
	
        return false;
    }

    public Packet constructPacket(byte[] data, int length){
        boolean isLast = false;
        byte fileID = data[1];
        if(data[0] % 2 == 0){
            // status is even => header packet
            String fileName = new String(data, 2, data.length - 3);
            HeaderPacket headerPacket = new HeaderPacket(fileID, fileName);
            return headerPacket;
        } else { // status is odd => data packet

            // if this is the last packet
            if(((data[0] >> 1) & 1) == 1){
                isLast = true;
            }
            // most and least significant bytes
            int msb = Byte.toUnsignedInt(data[2]);
            int lsb = Byte.toUnsignedInt(data[3]);
            int packetNumber = 256 * msb + lsb;

            //look for first null occurrence in packet for correct data size.
	    //i starts at 4 to skip over the status and fileID bytes
//            int i = 4;
//            while (i < data.length && data[i] != 0) {
//                i++;
//            }
	    // need the + 1 to change index value i into a length
            byte[] dataPortion = Arrays.copyOfRange(data, 4, length);

            DataPacket dataPacket = new DataPacket(fileID, isLast, packetNumber, dataPortion);
            return dataPacket;
        }
    }

    public void saveFiles() {
        for(int i = 0; i < 3; i++){
            files[i].constructFile();
        }
    } 
    
}
