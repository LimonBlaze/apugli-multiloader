package io.github.merchantpug.apugli.powers.fabric;

import io.github.merchantpug.apugli.networking.ApugliPackets;
import me.shedaniel.architectury.networking.NetworkManager;
import me.shedaniel.architectury.utils.GameInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class EdibleItemPowerImpl {
    public static void sendPackets(ServerPlayerEntity player, PacketByteBuf buf) {
        if (GameInstance.getServer() != null) {
            for (ServerWorld serverWorld : GameInstance.getServer().getWorlds()) {
                NetworkManager.sendToPlayers(serverWorld.getPlayers(), ApugliPackets.REMOVE_STACK_FOOD_COMPONENT, buf);
            }
        }

        NetworkManager.sendToPlayer(player, ApugliPackets.REMOVE_STACK_FOOD_COMPONENT, buf);
    }
}