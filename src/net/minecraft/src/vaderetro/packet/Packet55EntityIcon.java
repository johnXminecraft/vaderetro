package net.minecraft.src.vaderetro.packet;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraft.src.vaderetro.entity.EntityIcon;
import net.minecraft.src.vaderetro.enumeration.EnumIcon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet55EntityIcon extends Packet {

    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int direction;
    public String title;

    public Packet55EntityIcon() {
    }

    public Packet55EntityIcon(EntityIcon var1) {
        this.entityId = var1.entityId;
        this.xPosition = var1.xPosition;
        this.yPosition = var1.yPosition;
        this.zPosition = var1.zPosition;
        this.direction = var1.direction;
        this.title = var1.icon.title;
    }

    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.title = readString(var1, EnumIcon.maxIconTitleLength);
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.direction = var1.readInt();
    }

    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        writeString(this.title, var1);
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);
        var1.writeInt(this.direction);
    }

    public void processPacket(NetHandler var1) {
        var1.handleIcon(this);
    }

    public int getPacketSize() {
        return 24;
    }
}
