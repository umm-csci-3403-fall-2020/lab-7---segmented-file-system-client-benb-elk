package segmentedfilesystem;

public abstract class Packet {
    byte fileID;
    
    abstract byte getFileID();

    abstract boolean isHeaderPacket();
    
}
