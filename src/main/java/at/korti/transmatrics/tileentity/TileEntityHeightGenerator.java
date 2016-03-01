package at.korti.transmatrics.tileentity;

import net.minecraft.util.EnumFacing.AxisDirection;

/**
 * Created by Korti on 01.03.2016.
 */
public abstract class TileEntityHeightGenerator extends TileEntityGenerator {

    private int minHeight;
    private int maxHeight;
    private int minEnergyPerTick;
    private int maxEnergyPerTick;
    private AxisDirection direction;

    public TileEntityHeightGenerator(int minEnergyPerTick, int maxEnergyPerTick, int capacity, int maxExtract, int minHeight, int maxHeight, AxisDirection direction) {
        super(0, capacity, maxExtract);

        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minEnergyPerTick = minEnergyPerTick;
        this.maxEnergyPerTick = maxEnergyPerTick;
        this.direction = direction;
    }

    public TileEntityHeightGenerator(int minEnergyPerTick, int maxEnergyPerTick, int maxValue, int minHeight, int maxHeight, AxisDirection direction) {
        this(minEnergyPerTick, maxEnergyPerTick, maxValue, maxValue, minHeight, maxHeight, direction);
    }

    @Override
    public void onLoad() {
        super.energyPerTick = calcEnergyPerTick(minEnergyPerTick, maxEnergyPerTick, pos.getY(), minHeight, maxHeight, direction);
    }

    @Override
    public boolean canProduceEnergy() {
        if (direction.equals(AxisDirection.POSITIVE)) {
            return pos.getY() >= minHeight && pos.getY() <= maxHeight && super.canProduceEnergy();
        } else if (direction.equals(AxisDirection.NEGATIVE)) {
            return pos.getY() <= minHeight && pos.getY() >= maxHeight && super.canProduceEnergy();
        }
        return false;
    }

    private static int calcEnergyPerTick(int minEnergyPerTick, int maxEnergyPerTick, int y, int minHeight, int maxHeight, AxisDirection direction) {
        int heightDifferenceMinY = 0;
        int heightDifferenceMinMax = 0;

        if (direction.equals(AxisDirection.POSITIVE)) {
            heightDifferenceMinY = y - minHeight;
            heightDifferenceMinMax = maxHeight - minHeight;
        } else if(direction.equals(AxisDirection.NEGATIVE)) {
            heightDifferenceMinY = minHeight - y;
            heightDifferenceMinMax = minHeight - maxHeight;
        }

        if (heightDifferenceMinY < 0 || heightDifferenceMinMax < 0) {
            return 0;
        }

        float energyPerTick = (float)maxEnergyPerTick / (float)heightDifferenceMinMax;
        energyPerTick *= heightDifferenceMinY;
        return energyPerTick > minEnergyPerTick ? Math.round(energyPerTick) : minEnergyPerTick;
    }
}
