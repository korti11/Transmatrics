package at.korti.transmatrics.api.network.quantum;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

/**
 * Created by Korti on 26.06.2016.
 */
public class QuantumBridgePair extends WorldSavedData {

    public DimensionBlockPos quantumBridgeOne;
    public DimensionBlockPos quantumBridgeTwo;

    public QuantumBridgePair(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if(nbt.hasKey(NBT.QUANTUM_BRIDGE_ONE)) {
            this.quantumBridgeOne = DimensionBlockPos.readFromNBT(nbt.getCompoundTag(NBT.QUANTUM_BRIDGE_ONE));
        }
        if(nbt.hasKey(NBT.QUANTUM_BRIDGE_TWO)) {
            this.quantumBridgeTwo = DimensionBlockPos.readFromNBT(nbt.getCompoundTag(NBT.QUANTUM_BRIDGE_TWO));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound quantumBridgeOne = new NBTTagCompound();
        NBTTagCompound quantumBridgeTwo = new NBTTagCompound();
        if(this.quantumBridgeOne != null) {
            this.quantumBridgeOne.writeToNBT(quantumBridgeOne);
            nbt.setTag(NBT.QUANTUM_BRIDGE_ONE, quantumBridgeOne);
        }
        if(this.quantumBridgeTwo != null) {
            this.quantumBridgeTwo.writeToNBT(quantumBridgeTwo);
            nbt.setTag(NBT.QUANTUM_BRIDGE_TWO, quantumBridgeTwo);
        }
        return nbt;
    }


}
