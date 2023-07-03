package com.daqem.arc.data.condition.entity;

import com.daqem.arc.api.action.data.ActionData;
import com.daqem.arc.api.action.data.type.ActionDataType;
import com.daqem.arc.api.condition.AbstractCondition;
import com.daqem.arc.api.condition.serializer.ConditionSerializer;
import com.daqem.arc.api.condition.serializer.IConditionSerializer;
import com.daqem.arc.api.condition.type.ConditionType;
import com.daqem.arc.api.condition.type.IConditionType;
import com.google.gson.*;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityTypeCondition extends AbstractCondition {

    private final EntityType<?> entityType;

    public EntityTypeCondition(boolean inverted, EntityType<?> entityType) {
        super(inverted);
        this.entityType = entityType;
    }

    @Override
    public boolean isMet(ActionData actionData) {
        Entity entity = actionData.getData(ActionDataType.ENTITY);
        return entity != null && entity.getType() == entityType;
    }

    @Override
    public IConditionType<?> getType() {
        return ConditionType.ENTITY_TYPE;
    }

    @Override
    public IConditionSerializer<?> getSerializer() {
        return ConditionSerializer.ENTITY_TYPE;
    }

    public static class Serializer implements ConditionSerializer<EntityTypeCondition> {

        @Override
        public EntityTypeCondition fromJson(ResourceLocation location, JsonObject jsonObject, boolean inverted) {
            return new EntityTypeCondition(
                    inverted,
                    getEntityType(jsonObject, "entity_type"));
        }

        @Override
        public EntityTypeCondition fromNetwork(ResourceLocation location, FriendlyByteBuf friendlyByteBuf, boolean inverted) {
            return new EntityTypeCondition(
                    inverted,
                    friendlyByteBuf.readById(Registry.ENTITY_TYPE));
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, EntityTypeCondition type) {
            ConditionSerializer.super.toNetwork(friendlyByteBuf, type);
            friendlyByteBuf.writeId(Registry.ENTITY_TYPE, type.entityType);
        }
    }
}
