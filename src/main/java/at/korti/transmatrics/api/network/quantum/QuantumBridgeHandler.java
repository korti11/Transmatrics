package at.korti.transmatrics.api.network.quantum;

import at.korti.transmatrics.tileentity.network.TileEntityQuantumBridge;
import at.korti.transmatrics.util.helper.WorldHelper;
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

    private final QuantumBridgeMapper mapper;
    private static boolean hasChunkLoaded = false;

    private QuantumBridgeHandler() {
        mapper = loadMapper();
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
        QuantumBridgeMapper mapper = (QuantumBridgeMapper) world.loadData(QuantumBridgeMapper.class, MAPPER_ID);
        if (mapper == null) {
            mapper = new QuantumBridgeMapper(MAPPER_ID);
            world.setData(MAPPER_ID, mapper);
            mapper.setDirty(true);
        }
        return mapper;
    }

    private static World getWorld() {
        if (FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
            if(FMLCommonHandler.instance().getMinecraftServerInstance().worlds != null &&
                    FMLCommonHandler.instance().getMinecraftServerInstance().worlds.length > 0) {
                return FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0];
            }
        }
        return null;
    }

    public static String createQuantumBridge() {
        QuantumBridgeHandler instance = instance();
        World world = getWorld();
        if (world == null) {
            throw new RuntimeException("Couldn't create quantum bridge!");
        }
        String bridgeMapName = generateBridgeMapName();
        QuantumBridgePair bridgePair = new QuantumBridgePair(bridgeMapName);
        world.setData(bridgeMapName, bridgePair);
        bridgePair.setDirty(true);
        instance.mapper.addMapName(bridgeMapName);
        instance.mapper.setDirty(true);
        return bridgeMapName;
    }

    private static String generateBridgeMapName() {
        QuantumBridgeHandler instance = instance();
        instance.mapper.bridgeCount++;
        return "tm_quantum_bridge_" + instance.mapper.bridgeCount;
    }

    public static QuantumBridgePair getQuantumBridgePair(String bridgeMapName) {
        World world = getWorld();
        if (world == null) {
            throw new RuntimeException("Couldn't load quantum bridge!");
        }
        return (QuantumBridgePair) world.loadData(QuantumBridgePair.class, bridgeMapName);
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
        }
        else {
            throw new RuntimeException("Positions can not be override.");
        }
        bridgePair.setDirty(true);
    }

    public static void clearQuantumBridgePos(String bridgeMapName, DimensionBlockPos currentPos) {
        QuantumBridgePair bridgePair = getQuantumBridgePair(bridgeMapName);
        if (bridgePair == null) {
            throw new RuntimeException("Couldn't find ah quantum bridge with the map name: " + bridgeMapName);
        }
        if (currentPos.equals(bridgePair.quantumBridgeOne)) {
            bridgePair.quantumBridgeOne = null;
        } else if(currentPos.equals(bridgePair.quantumBridgeTwo)) {
            bridgePair.quantumBridgeTwo = null;
        }
        bridgePair.setDirty(true);
    }

    public static void forceChunkLoading() {
        if(!hasChunkLoaded) {
            List<String> bridgeNames = getQuantumBridgeMapNames();
            if (bridgeNames != null) {
                for (String bridgeName : bridgeNames) {
                    QuantumBridgePair bridgePair = getQuantumBridgePair(bridgeName);
                    TileEntityQuantumBridge bridge;
                    if (bridgePair.quantumBridgeOne != null) {
                        bridge = (TileEntityQuantumBridge) WorldHelper.getTileEntity(bridgePair.quantumBridgeOne);
                        bridge.forceChunkLoading();
                    }
                    if (bridgePair.quantumBridgeTwo != null) {
                        bridge = (TileEntityQuantumBridge) WorldHelper.getTileEntity(bridgePair.quantumBridgeTwo);
                        bridge.forceChunkLoading();
                    }
                }
            }
            hasChunkLoaded = true;
        }
    }

    public static List<String> getQuantumBridgeMapNames() {
        QuantumBridgeHandler instance = instance();
        if (instance.mapper != null) {
            return instance.mapper.getMapNames();
        }
        return null;
    }

}
