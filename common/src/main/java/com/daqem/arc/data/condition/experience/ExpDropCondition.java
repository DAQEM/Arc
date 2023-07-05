package com.daqem.arc.data.condition.experience;

import com.daqem.arc.api.action.data.ActionData;
import com.daqem.arc.api.action.data.type.ActionDataType;
import com.daqem.arc.api.condition.AbstractCondition;
import com.daqem.arc.api.condition.serializer.ConditionSerializer;
import com.daqem.arc.api.condition.serializer.IConditionSerializer;
import com.daqem.arc.api.condition.type.ConditionType;
import com.daqem.arc.api.condition.type.IConditionType;
import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ExpDropCondition extends AbstractCondition {

    private final int min;
    private final int max;

    public ExpDropCondition(boolean inverted, int min, int max) {
        super(inverted);
        this.min = min;
        this.max = max;

        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max for ExpDropCondition.");
        }
    }

    @Override
    public boolean isMet(ActionData actionData) {
        Integer expDrop = actionData.getData(ActionDataType.EXP_DROP);
        return expDrop != null && expDrop >= min && expDrop <= max;
    }

    @Override
    public IConditionType<?> getType() {
        return ConditionType.EXP_DROP;
    }

    @Override
    public IConditionSerializer<?> getSerializer() {
        return ConditionSerializer.EXP_DROP;
    }

    public static class Serializer implements ConditionSerializer<ExpDropCondition> {

        @Override
        public ExpDropCondition fromJson(ResourceLocation location, JsonObject jsonObject, boolean inverted) {
            return new ExpDropCondition(
                    inverted,
                    GsonHelper.getAsInt(jsonObject, "min"),
                    GsonHelper.getAsInt(jsonObject, "max"));
        }

        @Override
        public ExpDropCondition fromNetwork(ResourceLocation location, FriendlyByteBuf friendlyByteBuf, boolean inverted) {
            return new ExpDropCondition(
                    inverted,
                    friendlyByteBuf.readVarInt(),
                    friendlyByteBuf.readVarInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, ExpDropCondition type) {
            ConditionSerializer.super.toNetwork(friendlyByteBuf, type);
            friendlyByteBuf.writeVarInt(type.min);
            friendlyByteBuf.writeVarInt(type.max);
        }
    }
}
