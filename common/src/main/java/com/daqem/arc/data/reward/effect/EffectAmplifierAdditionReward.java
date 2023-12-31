package com.daqem.arc.data.reward.effect;

import com.daqem.arc.api.action.data.ActionData;
import com.daqem.arc.api.action.result.ActionResult;
import com.daqem.arc.api.action.data.type.ActionDataType;
import com.daqem.arc.api.reward.AbstractReward;
import com.daqem.arc.api.reward.serializer.IRewardSerializer;
import com.daqem.arc.api.reward.serializer.RewardSerializer;
import com.daqem.arc.api.reward.type.IRewardType;
import com.daqem.arc.api.reward.type.RewardType;
import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Objects;
import java.util.UUID;

public class EffectAmplifierAdditionReward extends AbstractReward {

    private final int addition;

    public EffectAmplifierAdditionReward(double chance, int priority, int addition) {
        super(chance, priority);
        this.addition = addition;
    }

    @Override
    public Component getDescription() {
        return getDescription(addition);
    }

    @Override
    public ActionResult apply(ActionData actionData) {
        MobEffectInstance effect = actionData.getData(ActionDataType.MOB_EFFECT_INSTANCE);
        if (effect != null) {
            if (actionData.getPlayer().arc$getPlayer() instanceof ServerPlayer player){
                MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), effect.getDuration(), Mth.floor(effect.getAmplifier() + addition), effect.isAmbient(), effect.isVisible());
                player.addEffect(newEffect, new ServerPlayer(Objects.requireNonNull(player.getServer()), player.serverLevel(), new GameProfile(UUID.randomUUID(), "a")));
            }
        }
        return new ActionResult();
    }

    @Override
    public IRewardType<?> getType() {
        return RewardType.EFFECT_AMPLIFIER_ADDITION;
    }

    @Override
    public IRewardSerializer<?> getSerializer() {
        return RewardSerializer.EFFECT_AMPLIFIER_ADDITION;
    }

    public static class Serializer implements RewardSerializer<EffectAmplifierAdditionReward> {

        @Override
        public EffectAmplifierAdditionReward fromJson(JsonObject jsonObject, double chance, int priority) {
            return new EffectAmplifierAdditionReward(
                    chance,
                    priority,
                    GsonHelper.getAsInt(jsonObject, "addition"));
        }

        @Override
        public EffectAmplifierAdditionReward fromNetwork(FriendlyByteBuf friendlyByteBuf, double chance, int priority) {
            return new EffectAmplifierAdditionReward(
                    chance,
                    priority,
                    friendlyByteBuf.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, EffectAmplifierAdditionReward type) {
            RewardSerializer.super.toNetwork(friendlyByteBuf, type);
            friendlyByteBuf.writeInt(type.addition);
        }
    }
}
