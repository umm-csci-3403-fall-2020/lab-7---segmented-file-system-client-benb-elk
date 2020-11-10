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

    @Override
    boolean isHeaderPacket() {
        return false;
    }

    public byte[] getData() {
        return data;
    }
    public boolean isLastPacket() {
        return isLast;
    }
    public int getPacketNumber() {
        return packetNumber;
    }

    
    
}
