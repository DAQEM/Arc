package com.daqem.arc;

import com.daqem.arc.config.ArcCommonConfig;
import com.daqem.arc.event.command.EventRegisterCommands;
import com.daqem.arc.event.triggers.AdvancementEvents;
import com.daqem.arc.event.triggers.BlockEvents;
import com.daqem.arc.event.triggers.EntityEvents;
import com.daqem.arc.event.triggers.ItemEvents;
import com.daqem.arc.networking.ArcNetworking;
import com.daqem.arc.player.brewing.BrewingStandData;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Arc {
    public static final String MOD_ID = "arc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<BlockPos, BrewingStandData> BREWING_STANDS = new HashMap<>();

    public static void initCommon() {
        registerEvents();
        ArcNetworking.init();
    }

    private static void registerEvents() {
        EventRegisterCommands.registerEvent();

        BlockEvents.registerEvents();
        ItemEvents.registerEvents();
        EntityEvents.registerEvents();
        AdvancementEvents.registerEvents();
    }

    public static ResourceLocation getId(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public static MutableComponent translatable(String str) {
        return Component.translatable(MOD_ID + "." + str);
    }

    public static MutableComponent translatable(String str, Object... objects) {
        return Component.translatable(MOD_ID + "." + str, objects);
    }

    public static MutableComponent literal(String str) {
        return Component.literal(str);
    }

    public static boolean isDebugEnvironment() {
        return ArcCommonConfig.isDebug.get();
    }
}
