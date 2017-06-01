package com.github.blank01.blank01.warps;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class Position {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private int worldId;

    public Position(double x, double y, double z, float yaw, float pitch, int worldId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.worldId = worldId;
    }
    public Position(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.worldId = 1;
    }
    public Position(BlockPos pos, float yaw, float pitch, int worldId) {
        this(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, yaw, pitch, worldId);
    }
    public Position(double x, double y, double z, int worldId) {
        this(x, y, z, 0, 0, worldId);
    }
    public Position(BlockPos pos, int worldId) {
        this(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, worldId);
    }
    public Position(EntityPlayerMP player) {
        this(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, player.worldObj.provider.getDimensionId());
    }

    
	public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public int getWorldId() {
        return worldId;
    }

}
