package com.daqem.arc.networking;

import com.daqem.arc.Arc;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ArcNetworking {

    CustomPacketPayload.Type<ClientboundUpdateActionsPacket> CLIENTBOUND_UPDATE_ACTIONS = new CustomPacketPayload.Type<>(Arc.getId("clientbound_update_actions"));
    CustomPacketPayload.Type<ClientboundUpdateActionHoldersPacket> CLIENTBOUND_UPDATE_ACTION_HOLDERS = new CustomPacketPayload.Type<>(Arc.getId("clientbound_update_action_holders"));
    CustomPacketPayload.Type<ClientboundActionScreenPacket> CLIENTBOUND_ACTION_SCREEN = new CustomPacketPayload.Type<>(Arc.getId("clientbound_action_screen"));
    CustomPacketPayload.Type<ClientboundSyncPlayerActionHoldersPacket> CLIENTBOUND_SYNC_PLAYER_ACTION_HOLDERS = new CustomPacketPayload.Type<>(Arc.getId("clientbound_sync_player_action_holders"));
    CustomPacketPayload.Type<ClientboundActionHoldersScreenPacket> CLIENTBOUND_ACTION_HOLDERS_SCREEN_PACKET = new CustomPacketPayload.Type<>(Arc.getId("clientbound_action_holders_screen_packet"));

    static void init() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, CLIENTBOUND_UPDATE_ACTIONS, ClientboundUpdateActionsPacket.STREAM_CODEC, ClientboundUpdateActionsPacket::handleClientSide);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, CLIENTBOUND_UPDATE_ACTION_HOLDERS, ClientboundUpdateActionHoldersPacket.STREAM_CODEC, ClientboundUpdateActionHoldersPacket::handleClientSide);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, CLIENTBOUND_ACTION_SCREEN, ClientboundActionScreenPacket.STREAM_CODEC, ClientboundActionScreenPacket::handleClientSide);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, CLIENTBOUND_SYNC_PLAYER_ACTION_HOLDERS, ClientboundSyncPlayerActionHoldersPacket.STREAM_CODEC, ClientboundSyncPlayerActionHoldersPacket::handleClientSide);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, CLIENTBOUND_ACTION_HOLDERS_SCREEN_PACKET, ClientboundActionHoldersScreenPacket.STREAM_CODEC, ClientboundActionHoldersScreenPacket::handleClientSide);
    }
}
