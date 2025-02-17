package com.daqem.arc.data.condition.item;

import com.daqem.arc.api.action.data.ActionData;
import com.daqem.arc.api.condition.AbstractCondition;
import com.daqem.arc.api.condition.serializer.IConditionSerializer;
import com.daqem.arc.api.condition.type.ConditionType;
import com.daqem.arc.api.condition.type.IConditionType;
import com.google.gson.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemInHandCondition extends AbstractCondition {

    private final ItemStack itemStack;
    private final InteractionHand hand;

    public ItemInHandCondition(boolean inverted, ItemStack itemStack, InteractionHand hand) {
        super(inverted);
        this.itemStack = itemStack;
        this.hand = hand;
    }

    @Override
    public Component getDescription() {
        return getDescription(itemStack.getHoverName(), hand.name().toLowerCase().replace("_", " "));
    }

    @Override
    public boolean isMet(ActionData actionData) {
        Player player = actionData.getPlayer().arc$getPlayer();
        return (hand == null && player.getMainHandItem().getItem() == itemStack.getItem() || player.getOffhandItem().getItem() == itemStack.getItem())
                || (hand != null && player.getItemInHand(hand).getItem() == itemStack.getItem());
    }

    @Override
    public IConditionType<?> getType() {
        return ConditionType.ITEM_IN_HAND;
    }

    public static class Serializer implements IConditionSerializer<ItemInHandCondition> {

        @Override
        public ItemInHandCondition fromJson(ResourceLocation location, JsonObject jsonObject, boolean inverted) {
            return new ItemInHandCondition(
                    inverted,
                    getItemStack(jsonObject.get("item")),
                    getHand(jsonObject, "hand"));
        }

        @Override
        public ItemInHandCondition fromNetwork(ResourceLocation location, RegistryFriendlyByteBuf friendlyByteBuf, boolean inverted) {
            return new ItemInHandCondition(
                    inverted,
                    ItemStack.STREAM_CODEC.decode(friendlyByteBuf),
                    friendlyByteBuf.readEnum(InteractionHand.class));
        }

        @Override
        public void toNetwork(RegistryFriendlyByteBuf friendlyByteBuf, ItemInHandCondition type) {
            IConditionSerializer.super.toNetwork(friendlyByteBuf, type);
            ItemStack.STREAM_CODEC.encode(friendlyByteBuf, type.itemStack);
            friendlyByteBuf.writeEnum(type.hand);
        }
    }
}
