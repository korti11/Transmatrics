package at.korti.transmatrics.event;

import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Korti
 * @since 10.10.2016
 */
public class EventHandler {

    @SubscribeEvent
    public void handleWorldLoad(WorldEvent.Load event) {
        QuantumBridgeHandler.forceChunkLoading();
    }

}
