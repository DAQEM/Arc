package com.daqem.arc.neoforge;

import com.daqem.arc.Arc;
import com.daqem.arc.client.ArcClient;
import com.daqem.arc.command.argument.ActionArgument;
import com.daqem.arc.neoforge.data.ActionManagerNeoForge;
import com.daqem.arc.neoforge.data.PlayerActionHolderManagerNeoForge;
import com.daqem.arc.registry.ArcRegistry;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;

public class SideProxyNeoForge {

    SideProxyNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        Arc.initCommon();

        NeoForge.EVENT_BUS.addListener(this::onAddReloadListeners);
        modEventBus.addListener(this::onRegisterRegistries);

        registerCommandArgumentTypes(modEventBus);
    }

    private void registerCommandArgumentTypes(IEventBus modEventBus) {
        DeferredRegister<ArgumentTypeInfo<?, ?>> argTypeRegistry = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Arc.MOD_ID);
        argTypeRegistry.register("action", () -> ArgumentTypeInfos.registerByClass(ActionArgument.class, SingletonArgumentInfo.contextFree(ActionArgument::action)));
        argTypeRegistry.register(modEventBus);
    }

    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PlayerActionHolderManagerNeoForge());
        event.addListener(new ActionManagerNeoForge());
    }

    @SubscribeEvent
    public void onRegisterRegistries(NewRegistryEvent event) {
        ArcRegistry.init();
    }

    public static class Server extends SideProxyNeoForge {
        Server(IEventBus modEventBus, ModContainer modContainer) {
            super(modEventBus, modContainer);
        }

    }

    public static class Client extends SideProxyNeoForge {

        Client(IEventBus modEventBus, ModContainer modContainer) {
            super(modEventBus, modContainer);
            ArcClient.init();
        }
    }
}
