package com.daqem.arc.data.action.block;

import com.daqem.arc.api.action.AbstractAction;
import com.daqem.arc.api.action.holder.type.IActionHolderType;
import com.daqem.arc.api.action.serializer.ActionSerializer;
import com.daqem.arc.api.action.serializer.IActionSerializer;
import com.daqem.arc.api.action.type.ActionType;
import com.daqem.arc.api.action.type.IActionType;

import com.daqem.arc.api.condition.ICondition;
import com.daqem.arc.api.reward.IReward;
import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class GetDestroySpeedAction extends AbstractAction {

    public GetDestroySpeedAction(ResourceLocation location, ResourceLocation actionHolderLocation, IActionHolderType<?> actionHolderType, boolean performOnClient, List<IReward> rewards, List<ICondition> conditions) {
        super(location, actionHolderLocation, actionHolderType, performOnClient, rewards, conditions);
    }

    @Override
    public IActionType<?> getType() {
        return ActionType.GET_DESTROY_SPEED;
    }

    @Override
    public IActionSerializer<?> getSerializer() {
        return ActionSerializer.GET_DESTROY_SPEED;
    }

    public static class Serializer implements ActionSerializer<GetDestroySpeedAction> {

        @Override
        public GetDestroySpeedAction fromJson(ResourceLocation location, JsonObject jsonObject, ResourceLocation actionHolderLocation, IActionHolderType<?> actionHolderType, boolean performOnClient, List<IReward> rewards, List<ICondition> conditions) {
            return new GetDestroySpeedAction(location, actionHolderLocation, actionHolderType, true, rewards, conditions);
        }

        @Override
        public GetDestroySpeedAction fromNetwork(ResourceLocation location, FriendlyByteBuf friendlyByteBuf, ResourceLocation actionHolderLocation, IActionHolderType<?> actionHolderType, boolean performOnClient, List<IReward> rewards, List<ICondition> conditions) {
            return new GetDestroySpeedAction(location, actionHolderLocation, actionHolderType, true, rewards, conditions);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, GetDestroySpeedAction type) {
            ActionSerializer.super.toNetwork(friendlyByteBuf, type);
        }
    }
}
