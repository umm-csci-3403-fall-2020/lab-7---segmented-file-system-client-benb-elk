package segmentedfilesystem;

public class DataPacket extends Packet{
    byte fileID;
    boolean isLast;
    int packetNumber;
    byte[] data;

    public DataPacket(byte fileID, boolean isLast, int packetNumber, byte[] data){
        this.fileID = fileID;
        this.isLast = isLast;
        this.packetNumber = packetNumber;
        this.data = data;
    }
    @Override
    byte getFileID() {
        return fileID;
    }
    private byte[] getData() {
        return data;
    }
    private boolean isLastPacket() {
        return isLast;
    }
    private int getPacketNumber() {
        return packetNumber;
    }

    
    
}
