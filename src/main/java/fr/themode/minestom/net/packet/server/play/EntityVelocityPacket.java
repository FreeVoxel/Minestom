package fr.themode.minestom.net.packet.server.play;

import fr.themode.minestom.net.packet.PacketWriter;
import fr.themode.minestom.net.packet.server.ServerPacket;

public class EntityVelocityPacket implements ServerPacket {

    public int entityId;
    public short velocityX, velocityY, velocityZ;

    @Override
    public void write(PacketWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeShort(velocityX);
        writer.writeShort(velocityY);
        writer.writeShort(velocityZ);
    }

    @Override
    public int getId() {
        return 0x46;
    }
}
