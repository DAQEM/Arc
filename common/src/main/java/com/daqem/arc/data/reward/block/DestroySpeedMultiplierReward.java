package com.daqem.arc.data.reward.block;

import com.daqem.arc.api.action.data.ActionData;
import com.daqem.arc.api.action.result.ActionResult;
import com.daqem.arc.api.reward.AbstractReward;
import com.daqem.arc.api.reward.serializer.IRewardSerializer;
import com.daqem.arc.api.reward.serializer.RewardSerializer;
import com.daqem.arc.api.reward.type.IRewardType;
import com.daqem.arc.api.reward.type.RewardType;
import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class DestroySpeedMultiplierReward extends AbstractReward {

    private final float multiplier;

    public DestroySpeedMultiplierReward(double chance, float multiplier) {
        super(chance);
        this.multiplier = multiplier;
    }

    @Override
    public ActionResult apply(ActionData actionData) {
        return new ActionResult().withDestroySpeedModifier(multiplier);
    }

    @Override
    public IRewardType<?> getType() {
        return RewardType.DESTROY_SPEED_MULTIPLIER;
    }

    @Override
    public IRewardSerializer<?> getSerializer() {
        return RewardSerializer.DESTROY_SPEED_MULTIPLIER;
    }

    public static class Serializer implements RewardSerializer<DestroySpeedMultiplierReward> {

        @Override
        public DestroySpeedMultiplierReward fromJson(JsonObject jsonObject, double chance) {
            return new DestroySpeedMultiplierReward(
                    chance,
                    GsonHelper.getAsFloat(jsonObject, "multiplier"));
        }

        @Override
        public DestroySpeedMultiplierReward fromNetwork(FriendlyByteBuf friendlyByteBuf, double chance) {
            return new DestroySpeedMultiplierReward(
                    chance,
                    friendlyByteBuf.readFloat());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, DestroySpeedMultiplierReward type) {
            RewardSerializer.super.toNetwork(friendlyByteBuf, type);
            friendlyByteBuf.writeFloat(type.multiplier);
        }
    }
}
