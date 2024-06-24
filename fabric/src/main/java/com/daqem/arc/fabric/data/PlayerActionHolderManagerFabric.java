package com.daqem.arc.fabric.data;

import com.daqem.arc.data.PlayerActionHolderManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class PlayerActionHolderManagerFabric extends PlayerActionHolderManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath("arc", "please_do_not_use_this");
    }
}
