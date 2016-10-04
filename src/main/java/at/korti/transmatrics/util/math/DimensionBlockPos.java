package at.korti.transmatrics.util.math;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/**
 * Created by Korti on 26.06.2016.
 */
public class DimensionBlockPos extends BlockPos {

    private int dimensionID;

    public DimensionBlockPos(int x, int y, int z, int dimensionID) {
        super(x, y, z);
        this.dimensionID = dimensionID;
    }

    public DimensionBlockPos(Vec3i source, int dimensionID) {
        super(source);
        this.dimensionID = dimensionID;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }
        tagCompound.setInteger(NBT.POS_X, getX());
        tagCompound.setInteger(NBT.POS_Y, getY());
        tagCompound.setInteger(NBT.POS_Z, getZ());
        tagCompound.setInteger(NBT.DIM_ID, dimensionID);
        return tagCompound;
    }

    public static DimensionBlockPos readFromNBT(NBTTagCompound tagCompound) {
        if (tagCompound != null) {
            int x = tagCompound.getInteger(NBT.POS_X);
            int y = tagCompound.getInteger(NBT.POS_Y);
            int z = tagCompound.getInteger(NBT.POS_Z);
            int dimID = tagCompound.getInteger(NBT.DIM_ID);
            return new DimensionBlockPos(x, y, z, dimID);
        }
        return null;
    }

    public int getDimensionID() {
        return dimensionID;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BlockPos)) {
            return false;
        } else if(!(object instanceof DimensionBlockPos)){
            return super.equals(object);
        } else if(dimensionID == ((DimensionBlockPos) object).dimensionID) {
            return super.equals(object);
        }
        return false;
    }
}
