package dev.smooth360;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;

/**
 * Smooth360: tap Mouse Button 4 to perform a short 360-degree camera spin.
 *
 * This changes only the local player's view rotation. It does not attack,
 * interact, send custom packets, or attempt to bypass server/anti-cheat rules.
 */
public final class Smooth360Client implements ClientModInitializer {
    private static final int SPIN_TICKS = 8; // 8 ticks = 0.4 seconds at 20 TPS
    private static final float TOTAL_ROTATION = 360.0F;

    private boolean wasButtonDown;
    private int ticksRemaining;
    private float startYaw;
    private float startPitch;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick(client));
    }

    private void tick(Minecraft client) {
        if (client.player == null || client.level == null || client.getOverlay() != null) {
            wasButtonDown = false;
            ticksRemaining = 0;
            return;
        }

        long window = client.getWindow().handle();
        boolean buttonDown = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_4)
                == GLFW.GLFW_PRESS;

        // Start once per press (not repeatedly while the button is held).
        if (buttonDown && !wasButtonDown && ticksRemaining == 0) {
            LocalPlayer player = client.player;
            startYaw = player.getYRot();
            startPitch = player.getXRot();
            ticksRemaining = SPIN_TICKS;
        }
        wasButtonDown = buttonDown;

        if (ticksRemaining > 0) {
            LocalPlayer player = client.player;
            int elapsed = SPIN_TICKS - ticksRemaining + 1;
            float progress = (float) elapsed / (float) SPIN_TICKS;

            // Ease-in/ease-out while completing exactly one full revolution.
            float eased = progress * progress * (3.0F - 2.0F * progress);
            player.setYRot(startYaw + TOTAL_ROTATION * eased);
            player.setXRot(startPitch);

            ticksRemaining--;
            if (ticksRemaining == 0) {
                // A full revolution ends at the original view direction.
                player.setYRot(startYaw);
                player.setXRot(startPitch);
            }
        }
    }
}
