package segmentedfilesystem;

import java.io.IOException;
import java.util.*;
import java.net.DatagramPacket;

public class PacketManager {

    public PacketManager() {
        List<ReceivedFile> files;
    }

    public void intake(DatagramPacket receivedPacket) {
        byte[] data;
        data = receivedPacket.getData();
        Packet packet = constructPacket(data);
    }
    public boolean allPacketsReceived() {
        return true;
    }
    public Packet constructPacket(byte[] data){
        boolean isLast = false;
        byte fileID = data[1];
        if(data[0] % 2 == 0){
            // status is even => header packet
            String fileName = new String(data, 2, data.length);
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
            byte[] dataPortion = Arrays.copyOfRange(data, 4, data.length);
            DataPacket dataPacket = new DataPacket(fileID, isLast, packetNumber, dataPortion);
            return dataPacket;

        }
    }
    
}
