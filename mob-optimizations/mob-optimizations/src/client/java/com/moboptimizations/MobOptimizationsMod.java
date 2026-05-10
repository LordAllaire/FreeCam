package com.moboptimizations;

import com.moboptimizations.feature.FreecamState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class MobOptimizationsMod implements ClientModInitializer {

    public static final String MOD_ID = "mob_optimizations";

    // --- Feature States ---
    public static boolean freecamEnabled = false;
    public static boolean xrayEnabled    = false;
    public static boolean noclipEnabled  = false;

    // Freecam camera position tracker
    public static FreecamState freecam = null;

    // --- Key Bindings ---
    public static KeyBinding freecamKey;
    public static KeyBinding xrayKey;
    public static KeyBinding noclipKey;

    @Override
    public void onInitializeClient() {

        freecamKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mob_optimizations.freecam",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                "category.mob_optimizations"
        ));

        xrayKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mob_optimizations.xray",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.mob_optimizations"
        ));

        noclipKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mob_optimizations.noclip",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.mob_optimizations"
        ));

        // ---- Tick handler ----
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            // Toggle Freecam
            while (freecamKey.wasPressed()) {
                toggleFreecam(client);
            }
            // Toggle X-Ray
            while (xrayKey.wasPressed()) {
                toggleXray(client);
            }
            // Toggle NoClip
            while (noclipKey.wasPressed()) {
                noclipEnabled = !noclipEnabled;
                sendActionbar(client, "NoClip", noclipEnabled);
            }

            // Move freecam each tick
            if (freecamEnabled && freecam != null && client.player != null) {
                float speed = client.options.sprintKey.isPressed() ? 1.5f : 0.5f;
                freecam.tickMove(client, speed);
            }
        });

        // ---- HUD overlay ----
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> renderHud(drawContext));
    }

    // -----------------------------------------------------------------------

    private static void toggleFreecam(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        freecamEnabled = !freecamEnabled;
        if (freecamEnabled) {
            freecam = new FreecamState(
                    client.player.getX(),
                    client.player.getEyeY(),
                    client.player.getZ(),
                    client.player.getYaw(),
                    client.player.getPitch()
            );
        } else {
            freecam = null;
        }
        sendActionbar(client, "Freecam", freecamEnabled);
    }

    private static void toggleXray(MinecraftClient client) {
        xrayEnabled = !xrayEnabled;
        // Force chunk rebuild so opacity changes take effect
        if (client.worldRenderer != null) {
            client.worldRenderer.reload();
        }
        sendActionbar(client, "X-Ray", xrayEnabled);
    }

    private static void sendActionbar(MinecraftClient client, String feature, boolean state) {
        if (client.player == null) return;
        String tag  = state ? Formatting.GREEN + "[ON]"  : Formatting.RED + "[OFF]";
        client.player.sendMessage(
                Text.literal(Formatting.AQUA + "[MobOpt] " + Formatting.WHITE + feature + " " + tag),
                true // action bar
        );
    }

    // -----------------------------------------------------------------------
    // HUD
    // -----------------------------------------------------------------------
    private void renderHud(DrawContext ctx) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) return;

        int y = 4;
        int x = 4;

        if (freecamEnabled) {
            ctx.drawTextWithShadow(client.textRenderer,
                    Text.literal(Formatting.AQUA + "[MobOpt] " + Formatting.GREEN + "FREECAM"),
                    x, y, 0xFFFFFF);
            y += 10;
        }
        if (xrayEnabled) {
            ctx.drawTextWithShadow(client.textRenderer,
                    Text.literal(Formatting.AQUA + "[MobOpt] " + Formatting.YELLOW + "X-RAY"),
                    x, y, 0xFFFFFF);
            y += 10;
        }
        if (noclipEnabled) {
            ctx.drawTextWithShadow(client.textRenderer,
                    Text.literal(Formatting.AQUA + "[MobOpt] " + Formatting.LIGHT_PURPLE + "NOCLIP"),
                    x, y, 0xFFFFFF);
        }
    }
}
