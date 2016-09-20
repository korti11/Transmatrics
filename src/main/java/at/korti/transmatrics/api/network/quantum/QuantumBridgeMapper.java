package at.korti.transmatrics.api.network.quantum;

import at.korti.transmatrics.api.Constants.NBT;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.WorldSavedData;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 26.06.2016.
 */
public final class QuantumBridgeMapper extends WorldSavedData {

    private final List<String> quantumBridgeMapNames;
    public int bridgeCount;

    public QuantumBridgeMapper(String name) {
        super(name);
        this.quantumBridgeMapNames = new LinkedList<>();
    }

    public void addMapName(String mapName) {
        this.quantumBridgeMapNames.add(mapName);
    }

    public List<String> getMapNames() {
        return ImmutableList.copyOf(quantumBridgeMapNames);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        quantumBridgeMapNames.clear();
        NBTTagList mapList = nbt.getTagList(NBT.QUANTUM_BRIDGE_MAP_NAMES, 10);
        for (int i = 0; i < mapList.tagCount(); i++) {
            if(mapList.get(i) instanceof NBTTagString) {
                quantumBridgeMapNames.add(mapList.getStringTagAt(i));
            }
        }
        bridgeCount = nbt.getInteger(NBT.QUANTUM_BRIDGE_COUNT);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList mapList = new NBTTagList();
        for (String mapName : quantumBridgeMapNames) {
            mapList.appendTag(new NBTTagString(mapName));
        }
        nbt.setTag(NBT.QUANTUM_BRIDGE_MAP_NAMES, mapList);
        nbt.setInteger(NBT.QUANTUM_BRIDGE_COUNT, bridgeCount);
        return nbt;
    }
}
