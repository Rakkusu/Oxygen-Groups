package austeretony.oxygen_groups.common.network.client;

import austeretony.oxygen.common.network.ProxyPacket;
import austeretony.oxygen_groups.client.GroupsManagerClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class CPAddPlayerToGroup extends ProxyPacket {

    private int index;

    public CPAddPlayerToGroup() {}

    public CPAddPlayerToGroup(int index) {
        this.index = index;
    }

    @Override
    public void write(PacketBuffer buffer, INetHandler netHandler) {
        buffer.writeInt(this.index);
    }

    @Override
    public void read(PacketBuffer buffer, INetHandler netHandler) {
        GroupsManagerClient.instance().addToGroup(buffer.readInt());
    }
}