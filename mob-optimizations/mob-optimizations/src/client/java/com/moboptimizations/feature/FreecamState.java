package com.moboptimizations.feature;

import net.minecraft.client.MinecraftClient;

/**
 * Holds the detached camera position/rotation for freecam mode.
 * WASD moves the camera horizontally, Space goes up, Shift goes down.
 * Sprint key (Ctrl) doubles speed.
 */
public class FreecamState {

    public double x, y, z;
    /** Stored for direction calculation; updated from player each tick. */
    public float  yaw, pitch;

    public FreecamState(double x, double y, double z, float yaw, float pitch) {
        this.x     = x;
        this.y     = y;
        this.z     = z;
        this.yaw   = yaw;
        this.pitch = pitch;
    }

    /**
     * Called every tick while freecam is active.
     * Reads the player's current look direction so mouse-look still works,
     * then moves the camera based on held movement keys.
     */
    public void tickMove(MinecraftClient client, float speed) {
        if (client.player == null) return;

        // Keep yaw/pitch in sync with the real player's mouse-look
        this.yaw   = client.player.getYaw();
        this.pitch = client.player.getPitch();

        double yawRad = Math.toRadians(this.yaw);

        // Forward / backward (+forward = W, -forward = S)
        float fwd = 0f, right = 0f, up = 0f;

        if (client.options.forwardKey.isPressed())  fwd   += speed;
        if (client.options.backKey.isPressed())     fwd   -= speed;
        if (client.options.rightKey.isPressed())    right += speed;
        if (client.options.leftKey.isPressed())     right -= speed;
        if (client.options.jumpKey.isPressed())     up    += speed;
        if (client.options.sneakKey.isPressed())    up    -= speed;

        // Horizontal movement aligned with look direction
        //   forward vector: (-sin(yaw), 0, cos(yaw))
        //   right   vector: ( cos(yaw), 0, sin(yaw))
        x += -Math.sin(yawRad) * fwd  +  Math.cos(yawRad) * right;
        z +=  Math.cos(yawRad) * fwd  +  Math.sin(yawRad) * right;
        y += up;
    }
}
