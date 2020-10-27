package segmentedfilesystem;

public class HeaderPacket extends Packet {
    byte fileID;
    String fileName;

    public HeaderPacket(byte fileID, String fileName){
        this.fileID = fileID;
        this.fileName = fileName;
    }

    @Override
    byte getFileID() {
        return fileID;
    }

    String getFileName() {
        return fileName;
    }
    
}
