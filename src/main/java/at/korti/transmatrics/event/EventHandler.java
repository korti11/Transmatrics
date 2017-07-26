package at.korti.transmatrics.event;

import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
