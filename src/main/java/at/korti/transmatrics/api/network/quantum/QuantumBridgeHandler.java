package at.korti.transmatrics.api.network.quantum;

import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

/**
 * Created by Korti on 26.06.2016.
 */
public final class QuantumBridgeHandler {

    private static QuantumBridgeHandler instance;

    private final static String MAPPER_ID = "tm_quantum_mapper";

    private final QuantumBridgeMapper quantumBridgeMapper;
    private int bridgeCounter = 0;

    private QuantumBridgeHandler() {
        quantumBridgeMapper = loadMapper();
    }

    public static QuantumBridgeHandler instance() {
        if (instance == null) {
            instance = new QuantumBridgeHandler();
        }
        return instance;
    }

    private QuantumBridgeMapper loadMapper() {
        World world = getWorld();
        if (world == null) {
            throw new RuntimeException("Couldn't load quantum bridge mapper from world!");
        }
        QuantumBridgeMapper mapper = (QuantumBridgeMapper) world.loadItemData(QuantumBridgeMapper.class, MAPPER_ID);
        if (mapper == null) {
            mapper = new QuantumBridgeMapper(MAPPER_ID);
            world.setItemData(MAPPER_ID, mapper);
        }
        return mapper;
    }

    private World getWorld() {
        if (FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
            return FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[0];
        }
        return null;
    }

    public static String createQuantumBridge() {
        QuantumBridgeHandler instance = instance();
        String bridgeMapName = generateBridgeMapName();
        QuantumBridgePair bridgePair = new QuantumBridgePair(bridgeMapName);
        World world = instance.getWorld();
        if (world == null) {
            throw new RuntimeException("Couldn't create quantum bridge!");
        }
        world.setItemData(bridgeMapName, bridgePair);
        return bridgeMapName;
    }

    private static String generateBridgeMapName() {
        QuantumBridgeHandler instance = instance();
        StringBuilder builder = new StringBuilder("tm_quantum_bridge_");
        builder.append(instance.bridgeCounter);
        instance.bridgeCounter++;
        return builder.toString();
    }

    public static QuantumBridgePair getQuantumBridgePair(String bridgeMapName) {
        QuantumBridgeHandler instance = instance();
        World world = instance.getWorld();
        if (world == null) {
            throw new RuntimeException("Couldn't load quantum bridge!");
        }
        return (QuantumBridgePair) world.loadItemData(QuantumBridgePair.class, bridgeMapName);
    }

    public static DimensionBlockPos getQuantumBridge(String bridgeMapName, boolean second) {
        QuantumBridgePair bridgePair = getQuantumBridgePair(bridgeMapName);
        if (bridgePair == null) {
            throw new RuntimeException("Couldn't find ah quantum bridge with the map name: " + bridgeMapName);
        }
        return !second ? bridgePair.quantumBridgeOne : bridgePair.quantumBridgeTwo;
    }

    public static DimensionBlockPos getDifferentQuantumBridgePos(String bridgeMapName, DimensionBlockPos currentPos) {
        QuantumBridgePair bridgePair = getQuantumBridgePair(bridgeMapName);
        if (bridgePair == null) {
            throw new RuntimeException("Couldn't find ah quantum bridge with the map name: " + bridgeMapName);
        }
        return currentPos.equals(bridgePair.quantumBridgeOne) ? bridgePair.quantumBridgeTwo :
                currentPos.equals(bridgePair.quantumBridgeTwo) ? bridgePair.quantumBridgeOne : null;
    }

    public static void setQuantumBridgePos(String bridgeMapName, DimensionBlockPos currentPos) {
        QuantumBridgePair bridgePair = getQuantumBridgePair(bridgeMapName);
        if (bridgePair == null) {
            throw new RuntimeException("Couldn't find ah quantum bridge with the map name: " + bridgeMapName);
        }
        if (bridgePair.quantumBridgeOne == null) {
            bridgePair.quantumBridgeOne = currentPos;
        } else if (bridgePair.quantumBridgeTwo == null) {
            bridgePair.quantumBridgeTwo = currentPos;
        } else {
            throw new RuntimeException("Positions can not be override.");
        }
    }

    public static void clearQuantumBridgePos(String bridgeMapName, DimensionBlockPos currentPos) {
        QuantumBridgePair bridgePair = getQuantumBridgePair(bridgeMapName);
        if (bridgeMapName == null) {
            throw new RuntimeException("Couldn't find ah quantum bridge with the map name: " + bridgeMapName);
        }
        if (currentPos.equals(bridgePair.quantumBridgeOne)) {
            bridgePair.quantumBridgeOne = null;
        } else if(currentPos.equals(bridgePair.quantumBridgeTwo)) {
            bridgePair.quantumBridgeTwo = null;
        }
    }

    public static List<String> getQuantumBridgeMapNames() {
        QuantumBridgeHandler instance = instance();
        if (instance.quantumBridgeMapper != null) {
            return instance.quantumBridgeMapper.getMapNames();
        }
        return null;
    }

}
