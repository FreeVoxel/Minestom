package fr.themode.minestom.net.packet.server.play;

import fr.themode.minestom.net.packet.PacketWriter;
import fr.themode.minestom.net.packet.server.ServerPacket;

public class MultiBlockChangePacket implements ServerPacket {

    public int chunkX;
    public int chunkZ;
    public BlockChange[] blockChanges;

    @Override
    public void write(PacketWriter writer) {
        writer.writeInt(chunkX);
        writer.writeInt(chunkZ);

        if (blockChanges != null) {
            int length = blockChanges.length;
            writer.writeVarInt(length);
            for (int i = 0; i < length; i++) {
                BlockChange blockChange = blockChanges[i];
                writer.writeByte(blockChange.positionXZ);
                writer.writeByte(blockChange.positionY);
                writer.writeVarInt(blockChange.newBlockId);
            }
        } else {
            writer.writeVarInt(0);
        }
    }

    @Override
    public int getId() {
        return 0x10;
    }

    public static class BlockChange {
        public byte positionXZ;
        public byte positionY;
        public int newBlockId;

    }
}
