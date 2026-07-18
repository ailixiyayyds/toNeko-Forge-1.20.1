package org.cneko.toneko.common.mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public final class ToNekoKeyBindings {
    public static KeyMapping LIE_KEY;
    public static KeyMapping GET_DOWN_KEY;
    public static KeyMapping RIDE_KEY;
    public static KeyMapping QUIRK_KEY;
    public static KeyMapping SPEED_KEY;
    public static KeyMapping JUMP_KEY;
    public static KeyMapping VISION_KEY;
    public static KeyMapping RIDE_HEAD_KEY;
    public static KeyMapping ROULETTE_KEY;
    public static KeyMapping NEKO_INFO_KEY;
    public static KeyMapping DISMOUNT_PASSENGER_KEY;
    public static KeyMapping TONEKO_MANAGEMENT_KEY;
    public static KeyMapping HUB_KEY;
    public static KeyMapping CHAT_WITH_NEKO_KEY;

    private static final String CATEGORY = "key.toneko.lie.category";
    private static boolean created;
    private static boolean fabricRegistered;

    private ToNekoKeyBindings() {
    }

    /** Creates the mappings without registering them to a loader event. */
    public static synchronized void createMappings() {
        if (created) return;
        created = true;

        LIE_KEY = mapping("key.toneko.lie", GLFW.GLFW_KEY_I);
        GET_DOWN_KEY = mapping("key.toneko.get_down", GLFW.GLFW_KEY_O);
        RIDE_KEY = mapping("key.toneko.ride", GLFW.GLFW_KEY_K);
        QUIRK_KEY = mapping("key.toneko.quirk", GLFW.GLFW_KEY_J);
        SPEED_KEY = mapping("key.toneko.speed", GLFW.GLFW_KEY_UNKNOWN);
        JUMP_KEY = mapping("key.toneko.jump", GLFW.GLFW_KEY_UNKNOWN);
        VISION_KEY = mapping("key.toneko.vision", GLFW.GLFW_KEY_UNKNOWN);
        RIDE_HEAD_KEY = mapping("key.toneko.ride_head", GLFW.GLFW_KEY_UNKNOWN);
        ROULETTE_KEY = mapping("key.toneko.roulette", GLFW.GLFW_KEY_Z);
        NEKO_INFO_KEY = mapping("key.toneko.neko_info", GLFW.GLFW_KEY_U);
        DISMOUNT_PASSENGER_KEY = mapping("key.toneko.dismount_passenger", GLFW.GLFW_KEY_G);
        TONEKO_MANAGEMENT_KEY = mapping("key.toneko.management", GLFW.GLFW_KEY_UNKNOWN);
        HUB_KEY = mapping("key.toneko.hub", GLFW.GLFW_KEY_LEFT_BRACKET);
        CHAT_WITH_NEKO_KEY = mapping("key.toneko.chat_with_neko", GLFW.GLFW_KEY_UNKNOWN);
    }

    /** Fabric entry point retained for the original loader module. */
    public static synchronized void init() {
        createMappings();
        if (fabricRegistered) return;
        fabricRegistered = true;
        allMappings().forEach(KeyBindingHelper::registerKeyBinding);
    }

    public static List<KeyMapping> allMappings() {
        createMappings();
        return List.of(
                LIE_KEY,
                GET_DOWN_KEY,
                RIDE_KEY,
                QUIRK_KEY,
                SPEED_KEY,
                JUMP_KEY,
                VISION_KEY,
                RIDE_HEAD_KEY,
                ROULETTE_KEY,
                NEKO_INFO_KEY,
                DISMOUNT_PASSENGER_KEY,
                TONEKO_MANAGEMENT_KEY,
                HUB_KEY,
                CHAT_WITH_NEKO_KEY
        );
    }

    private static KeyMapping mapping(String translationKey, int keyCode) {
        return new KeyMapping(
                translationKey,
                InputConstants.Type.KEYSYM,
                keyCode,
                CATEGORY
        );
    }
}
