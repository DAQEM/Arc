package com.daqem.arc.neoforge;

import com.daqem.arc.Arc;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Arc.MOD_ID)
public class ArcNeoForge {

    public ArcNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new SideProxyNeoForge.Client(modEventBus, modContainer);
        }
        else if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            new SideProxyNeoForge.Server(modEventBus, modContainer);
        } else {
            new SideProxyNeoForge(modEventBus, modContainer);
        }
    }
}
